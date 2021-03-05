/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package android.example.com.taipeizoointrodemo.networkApi

import com.squareup.moshi.Json

class MarsProperty(
        val id: String,
        // name的設定名稱是跟真正JSON裡面的KEY名稱一樣，後面的val則是自己定義，比較易讀。
        @Json(name = "img_src") val imgSrcUrl: String,
        val type: String,
        val price: Double
)
