package com.example.stickynotesapp.DI

import com.example.stickynotesapp.Retrofit.AuthInterceptor
import com.example.stickynotesapp.Retrofit.NoteAPI
import com.example.stickynotesapp.Retrofit.UserAPI
import com.example.stickynotesapp.Utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder() : Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpBuilder(authInterceptor: AuthInterceptor) : OkHttpClient.Builder{
        return OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
//            .addInterceptor(authInterceptor)
//            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClientWithInterceptor(authInterceptor: AuthInterceptor, okHttpBuilder: OkHttpClient.Builder) : OkHttpClient{
        return okHttpBuilder
            .addInterceptor(authInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder, okHttpBuilder: OkHttpClient.Builder) : UserAPI{
        return retrofitBuilder
            .client(okHttpBuilder.build())
            .build().
            create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder:Retrofit.Builder, okHttpClient: OkHttpClient) : NoteAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(NoteAPI :: class.java)
    }


}