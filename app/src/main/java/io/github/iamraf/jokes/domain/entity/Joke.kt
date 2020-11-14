package io.github.iamraf.jokes.domain.entity

data class Joke(
    val category: String,
    val delivery: String,
    val id: String,
    val setup: String
)