package io.github.iamraf.jokes.framework

import io.github.iamraf.jokes.data.model.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {
    @GET("joke/Any")
    suspend fun fetchRandomJokes(@Query("type") type: String = "twopart", @Query("amount") amount: Int = 10): Response<JokeResponse>
}