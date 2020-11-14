package io.github.iamraf.jokes.usecase

import io.github.iamraf.jokes.domain.JokesDataSource
import io.github.iamraf.jokes.domain.entity.Joke

class FetchRandomJokesUseCase(private val dataSource: JokesDataSource) {
    suspend fun fetchRandomJokes(): List<Joke> {
        return dataSource.fetchRandomJokes()
    }
}