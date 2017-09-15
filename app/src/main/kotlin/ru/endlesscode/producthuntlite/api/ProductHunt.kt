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

package ru.endlesscode.producthuntlite.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductHunt private constructor(api: ProductHuntApi) : ProductHuntApi by api {
    companion object {
        private val token = "591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff"

        private fun getAuthorizedClient(): OkHttpClient {
            return getClientWithHeaders(
                    "Accept" to "application/json",
                    "Content-Type" to "application/json",
                    "Authorization" to "Bearer $token",
                    "Host" to "api.producthunt.com"
            )
        }

        @get:Synchronized
        val api: ProductHunt by lazy {
            val retrofit = Retrofit.Builder()
                    .client(getAuthorizedClient())
                    .baseUrl("https://api.producthunt.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            ProductHunt(retrofit.create(ProductHuntApi::class.java))
        }

        private fun getClientWithHeaders(vararg headers: Pair<String, String>): OkHttpClient {
            return OkHttpClient().newBuilder().addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                headers.forEach { (key, value) ->
                    builder.addHeader(key, value)
                }
                chain.proceed(builder.build())
            }.build()
        }
    }
}