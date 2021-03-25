package de.vkay.tmdb.enumerations

import de.vkay.tmdb.internals.IValueEnum

enum class PersonGender(override val value: Int) : IValueEnum {
    OTHER(0),
    FEMALE(1),
    MALE(2)
}