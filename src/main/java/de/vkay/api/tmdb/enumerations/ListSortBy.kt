package de.vkay.api.tmdb.enumerations

import com.squareup.moshi.Json

enum class ListSortBy(private val jsonValue: String) {

    @Json(name = "original_order.asc")
    ORIGINAL_ORDER_ASC("original_order.asc"),

    @Json(name = "original_order.desc")
    ORIGINAL_ORDER_DESC("original_order.desc"),

    @Json(name = "release_date.asc")
    RELEASE_DATE_ASC("release_date.asc"),

    @Json(name = "release_date.desc")
    RELEASE_DATE_DESC("release_date.desc"),

    @Json(name = "title.asc")
    TITLE_ASC("title.asc"),

    @Json(name = "title.desc")
    TITLE_DESC("title.desc"),

    @Json(name = "vote_average.asc")
    VOTE_AVERAGE_ASC("vote_average.desc"),

    @Json(name = "vote_average.desc")
    VOTE_AVERAGE_DESC("vote_average.desc");

    override fun toString(): String = jsonValue
}