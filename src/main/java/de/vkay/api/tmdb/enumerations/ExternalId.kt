package de.vkay.api.tmdb.enumerations

@Suppress("SpellCheckingInspection")
enum class ExternalId {
    IMDB,
    TVDB,
    FACEBOOK,
    INSTAGRAM,
    TWITTER;

    override fun toString(): String {
        return when (ordinal) {
            0 -> "imdb_id"
            1 -> "tvdb_id"
            2 -> "facebook_id"
            3 -> "instagram_id"
            4 -> "twitter_id"
            else -> "UNKNOWN"
        }
    }
}