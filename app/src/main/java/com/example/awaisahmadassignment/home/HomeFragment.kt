package com.example.awaisahmadassignment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.awaisahmadassignment.common.data.MovieRepositoryImp
import com.example.awaisahmadassignment.common.data.api.TheMovieDbApi
import com.example.awaisahmadassignment.common.data.cache.MovieDatabase
import com.example.awaisahmadassignment.common.pagging.LoaderAdapter
import com.example.awaisahmadassignment.common.presentation.MovieAdapter
import com.example.awaisahmadassignment.common.utils.Constants
import com.example.awaisahmadassignment.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository =
                    MovieRepositoryImp(getMovieApi(), getMovieDatabase(requireContext()))
                return HomeViewModel(repository) as T
            }
        }
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = MovieAdapter()
        binding.moviesRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getMovieApi(): TheMovieDbApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(requireContext())
                    .collector(ChuckerCollector(requireContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build().create(TheMovieDbApi::class.java)
    }

    fun getMovieDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movieDb")
            .build()
    }
}