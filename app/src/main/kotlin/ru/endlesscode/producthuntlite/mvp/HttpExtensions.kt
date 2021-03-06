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

package ru.endlesscode.producthuntlite.mvp

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import retrofit2.Call
import ru.endlesscode.producthuntlite.api.ListResponse
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrThrow

fun <T> Call<out ListResponse<T>>.doInBackground(uiAction: (List<T>) -> Unit) = launch(CommonPool) {
    val result = awaitResult()
    val items = result.getOrThrow().get()

    run(UI) {
        uiAction(items)
    }
}

fun String.parametrize(vararg parameters: Pair<String, Any>): String {
    var result = this
    parameters.forEach { (name, value) ->
        val regex = Regex("[&|?]$name=\\w+")
        if (regex.containsMatchIn(this)) {
            result = regex.replace(result) {
                it.value.replaceAfter("$name=", value.toString())
            }
        } else {
            val prefix = when {
                result matches Regex(".*[?|&]$") -> ""
                result.contains('?') -> "&"
                else -> "?"
            }

            result += "$prefix$name=$value"
        }
    }

    return result
}
