package com.ngengeapps.backbasejobs.api

import com.ngengeapps.backbasejobs.models.Job
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    //val BASE_URL ="https://workatbackbase.com/"
    @GET("positions.json")
    suspend fun getJobs(@Query("description") description:String = "python" ):List<Job>

    companion object {

        val BASE_URL ="https://jobs.github.com/"

    }

}