package com.example.themoviedb.home.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.themoviedb.common.data.MovieRepositoryImp
import com.example.themoviedb.common.data.api.TheMovieDbApi
import com.example.themoviedb.common.data.cache.MovieDatabase
import com.example.themoviedb.common.domain.model.Movie
import com.example.themoviedb.common.pagging.LoaderAdapter
import com.example.themoviedb.common.presentation.MovieAdapter
import com.example.themoviedb.common.utils.Constants
import com.example.themoviedb.common.utils.DefaultDispatcher
import com.example.themoviedb.databinding.FragmentHomeBinding
import com.example.themoviedb.home.domain.FavoriteMoviesUseCase
import com.example.themoviedb.home.domain.GetAllMovies
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MovieRepositoryImp(
                    getMovieApi(requireContext()),
                    getMovieDatabase(requireContext())
                )
                return HomeViewModel(
                    DefaultDispatcher(),
                    GetAllMovies(repository),
                    FavoriteMoviesUseCase(repository)
                ) as T
            }
        }
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = MovieAdapter(::addToFavorites,::onMovieClick)
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

    private fun addToFavorites(movie: Movie) {
        viewModel.handleEvents(HomeMoviesEvent.AddToFavorites(movie))
    }

    private fun onMovieClick(movie: Movie){
        val action = HomeFragmentDirections.actionNavigationHomeToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getMovieApi(context: Context): TheMovieDbApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .collector(ChuckerCollector(context))
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
}