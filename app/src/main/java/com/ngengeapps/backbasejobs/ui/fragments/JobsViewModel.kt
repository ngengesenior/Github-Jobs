package com.ngengeapps.backbasejobs.ui.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngengeapps.backbasejobs.Repository
import com.ngengeapps.backbasejobs.models.Job
import com.ngengeapps.backbasejobs.models.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    private  val TAG = "JobsViewModel"
    private val _jobs = MutableLiveData<List<Job>>()
    val jobs:LiveData<List<Job>>
    get() = _jobs

    private val _currentJob = MutableLiveData<Job>(null)
    val currentJob:LiveData<Job?>
    get() = _currentJob

    private val __errorState = MutableLiveData(false)
    val errorState:LiveData<Boolean>
    get() = __errorState
    init {
        searchJobs("python")
    }

    fun setCurrentJob(job: Job) {
        _currentJob.value = job
    }

    fun searchJobs(description:String) {
        viewModelScope.launch {
            val result =  repository.getJobs(description)
            when(result) {
                is ResultOf.Success -> {
                    _jobs.value = result.data
                    Log.d(TAG, "searchJobs: Succeeded")
                }
                is ResultOf.Failure -> {
                    __errorState.value = true
                    Log.d(TAG, "searchJobs: Failed")
                }
            }

        }
    }
}