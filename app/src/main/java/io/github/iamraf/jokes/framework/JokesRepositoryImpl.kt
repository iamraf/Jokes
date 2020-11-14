package io.github.iamraf.jokes.framework

import io.github.iamraf.jokes.data.JokesRepository
import io.github.iamraf.jokes.data.model.JokeResponse
import io.github.iamraf.jokes.framework.exception.ResponseException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class JokesRepositoryImpl(private val api: JokesApi) : JokesRepository {
    override suspend fun fetchRandomJokes(): JokeResponse {
        try {
            val response = api.fetchRandomJokes()
            val body = response.body()

            return if (response.isSuccessful && body != null && body.jokes != null) {
                body
            } else {
                throw Exception()
            }
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> throw ResponseException("No internet connection")
                is SocketTimeoutException -> throw ResponseException("Connection timed out")
                else -> throw ResponseException("Unknown error occurred")
            }
        }
    }
}