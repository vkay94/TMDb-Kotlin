package de.vkay.api.tmdb.enumerations

import de.vkay.api.tmdb.internals.IValueEnum

enum class PersonGender(override val value: Int) : IValueEnum {
    OTHER(0),
    FEMALE(1),
    MALE(2)
}