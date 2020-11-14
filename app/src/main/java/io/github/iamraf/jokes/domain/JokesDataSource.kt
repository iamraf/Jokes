package io.github.iamraf.jokes.domain

import io.github.iamraf.jokes.domain.entity.Joke

interface JokesDataSource {
    suspend fun fetchRandomJokes(): List<Joke>
}