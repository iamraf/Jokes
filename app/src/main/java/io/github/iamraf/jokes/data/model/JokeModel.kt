package io.github.iamraf.jokes.data.model

data class JokeResponse(
    val jokes: List<JokeRemoteModel>?
)

data class JokeRemoteModel(
    val category: String?,
    val delivery: String?,
    val id: String?,
    val lang: String?,
    val joke: String?,
    val setup: String?,
    val type: String?
)