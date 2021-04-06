package de.vkay.api.tmdb.enumerations

class Trending {

    enum class Type {
        ALL, TV, MOVIE, PERSON;
        override fun toString(): String = name.toLowerCase()
    }

    enum class TimeWindow {
        DAY, WEEK;
        override fun toString(): String = name.toLowerCase()
    }
}