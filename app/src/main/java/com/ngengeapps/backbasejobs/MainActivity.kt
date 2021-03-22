package com.ngengeapps.backbasejobs

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ngengeapps.backbasejobs.models.Job
import com.ngengeapps.backbasejobs.ui.fragments.JobsViewModel
import com.ngengeapps.backbasejobs.ui.theme.BackbaseJobsTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImage
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    @Inject lateinit var repository: Repository


    private val viewModel:JobsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BackbaseJobsTheme {
                // A surface container using the 'background' color from the theme

                Surface(color = MaterialTheme.colors.background) {
                    setContentView(R.layout.activity_main)
                }
            }

            /*CoroutineScope(IO).launch {

                when(val result = repository.getJobs(description = "python")) {
                    is ResultOf.Success -> {
                        Log.d(TAG,"${result.data}")

                    }
                    is ResultOf.Failure-> {
                        Log.d(TAG,"${result.message}, ${result.throwable?.message}")
                    }
                }

            }*/
        }
    }
}



@Composable
fun JobDetails(viewModel: JobsViewModel,navController: NavController) {
    val job:Job? by viewModel.currentJob.observeAsState()
    Column() {
        CoilImage(modifier = Modifier
            .height(200.dp)
            .padding(2.dp) ,data = job?.company_logo!!,contentDescription = "An image",loading = {
            Box(Modifier.matchParentSize()){
                CircularProgressIndicator(Modifier.align(
                    Alignment.Center))
            }
        },error = {

        })
        
        Text(text = "Job Description")
        
        Text(text = job!!.description)

    }

}




