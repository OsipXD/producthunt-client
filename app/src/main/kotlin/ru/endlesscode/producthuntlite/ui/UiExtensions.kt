/*
 * This file is part of ProductHuntLite, licensed under the MIT License (MIT).
 *
 * Copyright (c) Osip Fatkullin <osip.fatkullin@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.endlesscode.producthuntlite.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import ru.endlesscode.producthuntlite.R
import ru.endlesscode.producthuntlite.misc.GlideApp
import ru.endlesscode.producthuntlite.mvp.parametrize
import ru.endlesscode.producthuntlite.ui.common.InfiniteScrollListener


@Suppress("UNCHECKED_CAST")
fun <T : View> ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false)
        = LayoutInflater.from(this.context).inflate(layoutId, this, attachToRoot) as T

fun RecyclerView.addThresholdListener(threshold: Int = 50, action: (Int) -> Unit): InfiniteScrollListener {
    val listener = InfiniteScrollListener(layoutManager as LinearLayoutManager, threshold, action)
    this.addOnScrollListener(listener)

    return listener
}

fun ImageView.whenWeKnowSize(init: ImageView.() -> Unit) {
    if (width == 0 && height == 0 && viewTreeObserver.isAlive) {
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                this@whenWeKnowSize.viewTreeObserver.removeOnGlobalLayoutListener(this)
                init()
            }
        })
    } else {
        init()
    }
}

fun ImageView.resizeAndLoad(url: String) {
    load(url.resize(width, height))
}

fun ImageView.load(url: String) {
    Log.d("loadingImage", url)

    when {
        ".gif" in url -> GlideApp.with(context).asGif().load(url)
        else -> GlideApp.with(context).asDrawable().load(url)
    }
            .error(R.color.colorAccent)
            .placeholder(R.color.colorPrimary)
            .centerCrop()
            .override(width, height)
            .into(this)
}

fun String.resize(width: Int, height: Int = width): String
        = this.parametrize("w" to width, "h" to height)

fun FragmentManager.commit(fragment: Fragment, init: FragmentTransaction.() -> FragmentTransaction) {
    this.beginTransaction()
            .add(R.id.main_activity_content, fragment, fragment.tag)
            .addToBackStack(null)
            .init()
            .commit()
}

fun RecyclerView.addDivider() {
    val divider = DividerItemDecoration(this.context, (layoutManager as LinearLayoutManager).orientation)
    this.addItemDecoration(divider)
}