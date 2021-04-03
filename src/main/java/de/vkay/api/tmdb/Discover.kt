package de.vkay.api.tmdb

import org.threeten.bp.LocalDate

class Discover {

    class ShowBuilder : HashMap<String, String>() {

        fun sortBy(sortBy: SortBy?) = apply { clearPut("sort_by", sortBy?.toString()) }
        fun airDateGreaterEqual(date: LocalDate?) = apply { clearPut("air_date.gte", date?.toString()) }
        fun airDateLessEqual(date: LocalDate?) = apply { clearPut("air_date.lte", date?.toString()) }
        fun firstAirDateGreaterEqual(date: LocalDate?) = apply { clearPut("first_air_date.gte", date?.toString()) }
        fun firstAirDateLessEqual(date: LocalDate?) = apply { clearPut("first_air_date.lte", date?.toString()) }
        fun firstAirDateYear(year: Int?) = apply { clearPut("first_air_date_year", year.toString()) }
        fun timeZone(timeZone: String?) = apply { clearPut("timezone", timeZone) }
        fun voteAverageGreaterEqual(voteAverage: Double?) = apply { clearPut("vote_average.gte", voteAverage.toString()) }
        fun voteCountGreaterEqual(voteCount: Int?) = apply { clearPut("vote_count.gte", voteCount.toString()) }
        fun withGenres(genreIds: List<Int>?) = apply { clearPut("with_genres", genreIds?.joinToString(separator = ",")) }
        fun withoutGenres(genreIds: List<Int>?) = apply { clearPut("without_genres", genreIds?.joinToString(separator = ",")) }
        fun withNetworks(networkIds: List<Int>?) = apply { clearPut("with_networks", networkIds?.joinToString(separator = ",")) }
        fun withRuntimeGreaterEqual(runtime: Int?) = apply { clearPut("with_runtime.gte", runtime.toString()) }
        fun withRuntimeLessEqual(runtime: Int?) = apply { clearPut("with_runtime.lte", runtime.toString()) }
        fun includeNullFirstAirDates(include: Boolean?) = apply { clearPut("include_null_first_air_dates", include.toString()) }
        fun withOriginalLanguage(langCode: String?) = apply { clearPut("with_original_language", langCode) }
        fun withoutKeywords(keywordIds: List<Int>?) = apply { clearPut("without_keywords", keywordIds?.joinToString(separator = ",")) }
        fun screenedTheatrically(include: Boolean?) = apply { clearPut("screened_theatrically", include.toString()) }
        fun withCompanies(companyIds: List<Int>?) = apply { clearPut("with_companies", companyIds?.joinToString(separator = ",")) }
        fun withKeywords(keywordIds: List<Int>?) = apply { clearPut("with_keywords", keywordIds?.joinToString(separator = ",")) }
        fun withWatchProviders(watchProviderIds: List<Int>?) = apply { clearPut("with_watch_providers", watchProviderIds?.joinToString(separator = ",")) }
        fun watchRegion(region: String?) = apply { clearPut("watch_region", region) }

        private fun clearPut(key: String, value: String?) {
            remove(key)
            value?.let { put(key, value) }
        }
    }

    class MovieBuilder : HashMap<String, String>() {

        fun sortBy(sortBy: SortBy?) = apply { clearPut("sort_by", sortBy?.toString()) }
        fun includeAdult(include: Boolean?) = apply { clearPut("include_adult", include.toString()) }
        fun includeVideo(include: Boolean?) = apply { clearPut("include_video", include.toString()) }
        fun primaryReleaseYear(year: Int?) = apply { clearPut("primary_release_year", year.toString()) }
        fun primaryReleaseDateGreaterEqual(date: LocalDate?) = apply { clearPut("primary_release_date.gte", date?.toString()) }
        fun primaryReleaseDateLessEqual(date: LocalDate?) = apply { clearPut("primary_release_date.lte", date?.toString()) }
        fun releaseDateGreaterEqual(date: LocalDate?) = apply { clearPut("release_date.gte", date?.toString()) }
        fun releaseDateLessEqual(date: LocalDate?) = apply { clearPut("release_date.lte", date?.toString()) }
        fun year(year: Int?) = apply { clearPut("year", year.toString()) }

        fun voteAverageGreaterEqual(voteAverage: Double?) = apply { clearPut("vote_average.gte", voteAverage.toString()) }
        fun voteCountGreaterEqual(voteCount: Int?) = apply { clearPut("vote_count.gte", voteCount.toString()) }
        fun voteAverageLessEqual(voteAverage: Double?) = apply { clearPut("vote_average.lte", voteAverage.toString()) }
        fun voteCountLessEqual(voteCount: Int?) = apply { clearPut("vote_count.lte", voteCount.toString()) }

        fun withCast(castIds: List<Int>?) = apply { clearPut("with_cast", castIds?.joinToString(separator = ",")) }
        fun withCrew(crewIds: List<Int>?) = apply { clearPut("with_crew", crewIds?.joinToString(separator = ",")) }
        fun withPeople(personIds: List<Int>?) = apply { clearPut("with_people", personIds?.joinToString(separator = ",")) }
        fun withGenres(genreIds: List<Int>?) = apply { clearPut("with_genres", genreIds?.joinToString(separator = ",")) }
        fun withoutGenres(genreIds: List<Int>?) = apply { clearPut("without_genres", genreIds?.joinToString(separator = ",")) }
        fun withKeywords(keywordIds: List<Int>?) = apply { clearPut("with_keywords", keywordIds?.joinToString(separator = ",")) }
        fun withoutKeywords(keywordIds: List<Int>?) = apply { clearPut("without_keywords", keywordIds?.joinToString(separator = ",")) }

        fun withRuntimeGreaterEqual(runtime: Int?) = apply { clearPut("with_runtime.gte", runtime.toString()) }
        fun withRuntimeLessEqual(runtime: Int?) = apply { clearPut("with_runtime.lte", runtime.toString()) }
        fun withOriginalLanguage(langCode: String?) = apply { clearPut("with_original_language", langCode) }
        fun withWatchProviders(watchProviderIds: List<Int>?) = apply { clearPut("with_watch_providers", watchProviderIds?.joinToString(separator = ",")) }
        fun watchRegion(region: String?) = apply { clearPut("watch_region", region) }

        private fun clearPut(key: String, value: String?) {
            remove(key)
            value?.let { put(key, value) }
        }
    }

    enum class SortBy(private val queryValue: String) {
        VOTE_AVERAGE_DESC("vote_average.desc"),
        VOTE_AVERAGE_ASC("vote_average.asc"),
        POPULARITY_DESC("popularity.desc"),
        POPULARITY_ASC("popularity.asc"),
        FIRST_AIR_DATE_DESC("first_air_date.desc"),
        FIRST_AIR_DATE_ASC("first_air_date.asc");

        override fun toString(): String = queryValue
    }
}