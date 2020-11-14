package io.github.iamraf.jokes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.iamraf.jokes.BuildConfig
import io.github.iamraf.jokes.data.JokesDataSourceImpl
import io.github.iamraf.jokes.data.JokesRepository
import io.github.iamraf.jokes.domain.JokesDataSource
import io.github.iamraf.jokes.framework.JokesApi
import io.github.iamraf.jokes.framework.JokesRepositoryImpl
import io.github.iamraf.jokes.usecase.FetchRandomJokesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object JokesModule {
    @Provides
    fun provideJokesRepository(api: JokesApi): JokesRepository {
        return JokesRepositoryImpl(api)
    }

    @Provides
    fun provideJokesDataSource(repository: JokesRepository): JokesDataSource {
        return JokesDataSourceImpl(repository)
    }

    @Provides
    fun provideFetchRandomJokesUseCase(dataSource: JokesDataSource): FetchRandomJokesUseCase {
        return FetchRandomJokesUseCase(dataSource)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): JokesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sv443.net/jokeapi/v2/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(JokesApi::class.java)
    }
}