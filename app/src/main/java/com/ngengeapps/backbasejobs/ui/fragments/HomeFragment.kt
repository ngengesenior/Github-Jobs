package com.ngengeapps.backbasejobs.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ngengeapps.backbasejobs.models.Job
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImage

import androidx.compose.runtime.setValue

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: JobsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return ComposeView(requireContext()).apply {
            setContent {
                val jobs: List<Job> by viewModel.jobs.observeAsState(listOf())
                var searchItem by rememberSaveable { mutableStateOf("") }

                Column() {
                    OutlinedTextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                        value = searchItem,
                        onValueChange = { searchItem = it },
                        label = {
                            Text(text = "Search Job.. eg python")
                        },
                        singleLine = true,
                        trailingIcon = {

                            IconButton(
                                onClick = {
                                    viewModel.searchJobs(searchItem)
                                    searchItem = ""
                                },
                            ) {
                                Icon(Icons.Filled.Search, contentDescription = "Search Icon")
                            }
                        })
                    Spacer(modifier = Modifier.height(6.dp))


                    if (jobs.isNullOrEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(200.dp))

                        }
                    } else {

                        LazyColumn(contentPadding = PaddingValues(4.dp)) {
                            items(jobs, key = { it.id }) { job ->
                                JobItemView(job = job, onItemClick = {
                                    viewModel.setCurrentJob(job)
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                                            job
                                        )
                                    )

                                })
                            }
                        }
                    }

                }


            }
        }
    }

}


@Composable
fun JobItemView(modifier: Modifier = Modifier, job: Job, onItemClick: (Job) -> Unit) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = {
                onItemClick(job)
            }), elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            when (job.company_logo) {
                is String -> {
                    CoilImage(modifier = Modifier
                        .size(60.dp)
                        .padding(2.dp),
                        data = job.company_logo as String,
                        contentDescription = "An image",
                        loading = {
                            Box(Modifier.matchParentSize()) {
                                CircularProgressIndicator(
                                    Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }, error = {

                        })
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(2.dp)
                            .background(color = Color.Black),
                    )
                }

            }


            Column(verticalArrangement = Arrangement.Center) {
                Text(text = job.title, fontWeight = FontWeight.Bold)
                Text(text = job.company)

            }

            Spacer(modifier = Modifier.weight(1f))
            Text(text = job.type, modifier = Modifier.padding(horizontal = 3.dp))

        }

    }

}

