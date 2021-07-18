package de.vkay.api.tmdb.enumerations

import java.util.Locale

class Trending {

    enum class Type {
        ALL, TV, MOVIE, PERSON;
        override fun toString(): String = name.lowercase(Locale.ROOT)
    }

    enum class TimeWindow {
        DAY, WEEK;
        override fun toString(): String = name.lowercase(Locale.ROOT)
    }
}