package com.applications.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.applications.common.pagging.LoaderAdapter
import com.applications.domain.entities.Movie
import com.applications.home.databinding.FragmentHomeBinding
import com.applications.ui.MovieAdapter
import com.applications.utils.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = MovieAdapter(::addToFavorites, ::onMovieClick)
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
        val request = NavDeepLinkRequest.Builder.fromUri(
                "android-app://com.example.themoviedb/movieDetailFragment?movie=${
                    JsonParser.toJson(
                        movie
                    )
                }".toUri()
            )
            .build()
        findNavController().navigate(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}