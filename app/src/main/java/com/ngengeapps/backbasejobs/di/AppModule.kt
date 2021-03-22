package com.ngengeapps.backbasejobs.di
import com.ngengeapps.backbasejobs.api.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl(APIService.BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit):APIService = retrofit.create(APIService::class.java)


}