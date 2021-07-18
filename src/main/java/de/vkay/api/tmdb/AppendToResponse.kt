/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.vkay.api.tmdb

import java.util.Locale

class AppendToResponse(private vararg val appendItems: Item = arrayOf()) {
    private val seasons = mutableListOf<Int>()

    override fun toString(): String {
        var result = ""
        result += appendItems.joinToString(separator = ",") { it.toString() }
        if (seasons.isNotEmpty()) {
            result += if (appendItems.isNotEmpty()) "," else ""
            result += seasons.joinToString(separator = ",") { "season/$it" }
        }
        return result
    }

    enum class Item(private val key: String) {
        IMAGES(""), VIDEOS(""), EXTERNAL_IDS(""),
        RECOMMENDATIONS(""), SIMILAR(""), KEYWORDS(""),
        WATCH_PROVIDERS("watch/providers");

        override fun toString(): String = key.ifBlank { name.lowercase(Locale.ROOT) }
    }
}