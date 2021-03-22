package com.ngengeapps.backbasejobs.ui.fragments

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ngengeapps.backbasejobs.models.Job
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImage
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.viewinterop.AndroidView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    val viewModel:JobsViewModel by activityViewModels()
    //val job:Job by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val state = rememberLazyListState()
                val job:Job? by viewModel.currentJob.observeAsState()
                LazyColumn(state = state) {
                    // Check if there is a company logo URL
                    when(job?.company_logo) {
                        is String -> {
                            item{
                                CoilImage(modifier = Modifier
                                    .height(200.dp)
                                    .padding(2.dp) ,data = job?.company_logo!!,contentDescription = "An image",loading = {
                                    Box(Modifier.matchParentSize()){
                                        CircularProgressIndicator(
                                            Modifier.align(
                                                Alignment.Center))
                                    }
                                },error = {

                                })
                            }


                        } else -> {
                        item{
                            Box(modifier = Modifier
                                .height(200.dp)
                                .padding(2.dp)
                                .background(color = Color.Black) ,)

                        }
                        }

                    }
                    item {
                        if (job?.company_url != null) {
                            Row() {
                                Text("Company Website:")
                                Text(text = "${job?.company_url}",textDecoration = TextDecoration.Underline,color = Color.Blue)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                    }


                    item {
                        Text(text = "Job Description",fontWeight = FontWeight.Bold,textDecoration = TextDecoration.Underline)
                    }

                    item{
                        //val webView = WebView(requireContext())
                        val encodedHtml = Base64.encodeToString(job!!.description.toByteArray(), Base64.NO_PADDING)
                        //webView.loadData(encodedHtml,"text/html", "base64")
                        AndroidView(factory = { context ->
                            WebView(requireContext()).apply {
                                loadData(encodedHtml,"text/html", "base64")
                            }
                            //webView

                        })


                        /*Card() {
                            Text(text = job!!.description)
                        }*/

                    }

                    item {
                        OutlinedButton(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp) ,onClick = {
                            Toast.makeText(requireContext(),"Hello",Toast.LENGTH_LONG).show()
                        }) {
                            Text(text = "Apply")
                        }
                    }

                }

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}