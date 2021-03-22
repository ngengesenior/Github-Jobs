package com.ngengeapps.backbasejobs

import com.ngengeapps.backbasejobs.api.APIService
import com.ngengeapps.backbasejobs.models.ResultOf
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class Repository @Inject constructor(private val apiService: APIService){
     suspend fun getJobs(description: String):ResultOf {
        return try {
            val response = apiService.getJobs(description)
            ResultOf.Success(response)

        } catch (ioEx:IOException) {
            ResultOf.Failure(message = "io Failure",throwable = ioEx)
        } catch (httpEx:HttpException) {
            ResultOf.Failure(message = "http Failure",throwable = httpEx)
        }
    }

}

