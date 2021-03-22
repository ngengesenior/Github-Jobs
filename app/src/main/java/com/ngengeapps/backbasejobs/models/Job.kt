package com.ngengeapps.backbasejobs.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val type:String,
    val company:String,
    val created_at:String,
    val url:String,
    val title:String,
    val description:String,
    val company_url:String?,
    val id:String,
    val company_logo:String?,
    val how_to_apply:String
):Parcelable

sealed class ResultOf{
    data class Success(val data:List<Job>):ResultOf()
    data class Failure(
        val message:String?,
        val throwable: Throwable?
    ):ResultOf()

}
