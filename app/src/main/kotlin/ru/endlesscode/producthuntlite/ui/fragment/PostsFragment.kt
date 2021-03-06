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

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.posts_fragment.*
import ru.endlesscode.producthuntlite.R
import ru.endlesscode.producthuntlite.mvp.model.Item
import ru.endlesscode.producthuntlite.mvp.model.Post
import ru.endlesscode.producthuntlite.mvp.presenter.PostsPresenter
import ru.endlesscode.producthuntlite.ui.activity.MainActivity
import ru.endlesscode.producthuntlite.ui.adapter.PostsAdapter
import ru.endlesscode.producthuntlite.ui.addDivider

class PostsFragment : ItemsFragment<PostsPresenter>() {

    companion object {
        fun instance(topic: Item): PostsFragment {
            val args = Bundle()
            args.putInt(PostsPresenter.TOPIC_ID, topic.id)
            args.putString(PostsPresenter.TOPIC_NAME, topic.name)

            val fragment = PostsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    override lateinit var presenter: PostsPresenter

    override val layoutId = R.layout.posts_fragment
    override val itemsRefresh: SwipeRefreshLayout by lazy { posts_refresh }
    override val toolbar: Toolbar by lazy { posts_toolbar }
    override val itemsListId: Int by lazy { R.id.posts_list }

    override val title: String by lazy { arguments.getString(PostsPresenter.TOPIC_NAME) }

    @ProvidePresenter
    fun providePresenter() = PostsPresenter(
            arguments.getInt(PostsPresenter.TOPIC_ID)
    )

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsList.addDivider()
        toolbar.setNavigationOnClickListener {
            val activity = context as MainActivity
            activity.onBackPressed()
        }
    }

    override fun createAdapter() = PostsAdapter(presenter)

    override fun openItem(item: Item) {
        val activity = activity as MainActivity
        activity.changeFragment(PostDetailsFragment.instance(item as Post))
    }
}

