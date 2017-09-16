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

package ru.endlesscode.producthuntlite.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.topics_fragment.*
import ru.endlesscode.producthuntlite.R
import ru.endlesscode.producthuntlite.mvp.common.Item
import ru.endlesscode.producthuntlite.mvp.presenter.TopicsPresenter
import ru.endlesscode.producthuntlite.ui.activity.MainActivity
import ru.endlesscode.producthuntlite.ui.adapter.TopicsAdapter

class TopicsFragment : ItemsFragment<TopicsPresenter>() {
    @InjectPresenter
    override lateinit var presenter: TopicsPresenter

    override val layoutId = R.layout.topics_fragment
    override val itemsRefresh: SwipeRefreshLayout by lazy { topics_refresh }
    override val toolbar: Toolbar by lazy { topics_toolbar }
    override val itemsListId: Int by lazy { R.id.topics_list }

    override val title = "Topics"

    override fun createAdapter() = TopicsAdapter(presenter)

    override fun openItem(item: Item) {
        val activity = activity as MainActivity
        activity.changeFragment(PostsFragment.instance(item))
    }
}