package io.github.iamraf.jokes.data

import io.github.iamraf.jokes.data.model.JokeResponse
import io.github.iamraf.jokes.domain.JokesDataSource
import io.github.iamraf.jokes.domain.entity.Joke

class JokesDataSourceImpl(private val repository: JokesRepository) : JokesDataSource {
    override suspend fun fetchRandomJokes(): List<Joke> {
        val response: JokeResponse = repository.fetchRandomJokes()

        return response.jokes?.map {
            Joke(
                id = it.id ?: "",
                category = it.category ?: "",
                delivery = it.delivery ?: "",
                setup = it.setup ?: ""
            )
        } ?: emptyList()
    }
}