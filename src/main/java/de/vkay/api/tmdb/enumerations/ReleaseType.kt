package de.vkay.api.tmdb.enumerations

import de.vkay.api.tmdb.internals.IValueEnum

enum class ReleaseType(override val value: Int) : IValueEnum {
    PREMIERE(1),
    THEATRICAL_LIMITED(2),
    THEATRICAL(3),
    DIGITAL(4),
    PHYSICAL(5),
    TV(6),

    UNKNOWN(-1)
}