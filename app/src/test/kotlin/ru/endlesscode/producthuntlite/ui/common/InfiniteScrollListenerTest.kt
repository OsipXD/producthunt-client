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

package ru.endlesscode.producthuntlite.ui.common

import android.support.v7.widget.LinearLayoutManager
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class InfiniteScrollListenerTest {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var listener: InfiniteScrollListener

    @Before
    fun setUp() {
        layoutManager = mock {
            on { itemCount } doReturn 10
        }

        listener = spy(InfiniteScrollListener(layoutManager, 3, { }))
        listener.onUpdate()
    }

    @Test
    fun onScrolled_whenScrolledUpShouldCallNothing() {
        listener.onScrolled(mock(), 0, -1)

        verify(listener, never()).onScrollDown()
    }

    @Test
    fun onScrolled_whenScrollDownTwiceShouldOutOfThresholdOnce() {
        layoutManager.stub {
            on { findLastVisibleItemPosition() } doReturn 7
        }

        listener.onScrolled(mock(), 0, 1)

        verify(listener, times(1)).onOutOfThreshold()
    }

    @Test
    fun onScrolled_whenScrollDownShouldOutOfThreshold() {
        layoutManager.stub {
            on { findLastVisibleItemPosition() } doReturn 7
        }

        listener.onScrolled(mock(), 0, 1)
        listener.onScrolled(mock(), 0, 1)

        verify(listener, times(1)).onOutOfThreshold()
    }

    @Test
    fun onScrolled_whenScrollDownShouldNotOutOfThreshold() {
        layoutManager.stub {
            on { findLastVisibleItemPosition() } doReturn 5
        }

        listener.onScrolled(mock(), 0, 1)

        verify(listener, never()).onOutOfThreshold()
    }


}