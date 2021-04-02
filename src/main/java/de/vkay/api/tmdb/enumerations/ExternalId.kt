package de.vkay.api.tmdb.enumerations

enum class ExternalId {
    IMDB,
    TVDB,
    FACEBOOK,
    INSTAGRAM,
    TWITTER;

    override fun toString(): String {
        return when (name) {
            "IMDB" -> "imdb_id"
            "TVDB" -> "tvdb_id"
            "FACEBOOK" -> "facebook_id"
            "INSTAGRAM" -> "instagram_id"
            "TWITTER" -> "twitter_id"
            else -> "UNKNOWN"
        }
    }
}