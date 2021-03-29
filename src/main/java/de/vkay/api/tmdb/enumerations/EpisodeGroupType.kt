package de.vkay.api.tmdb.enumerations

import de.vkay.api.tmdb.internals.IValueEnum

enum class EpisodeGroupType(override val value: Int) : IValueEnum {
    ORIGINAL_AIR_DATE(1),
    ABSOLUTE(2),
    DVD(3),
    DIGITAl(4),
    STORY_ARC(5),
    PRODUCTION(6),
    TV(7),
    UNDEFINED(-1)
}