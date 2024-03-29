package com.applications.favorite.presentation

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.applications.domain.entities.Movie
import com.applications.favorite.databinding.FragmentFavoritesBinding
import com.applications.ui.FavoriteMoviesAdapter
import com.applications.utils.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoritesMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FavoriteMoviesAdapter(::onMovieClick, ::addToFavorites)
        binding.favoritesMoviesRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.favoritesMoviesRecyclerView.adapter=adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesViewModel.favoriteMovies.collect {
                    adapter.submitList(it)
                }
            }
        }
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

    private fun addToFavorites(movie: Movie) {
        favoritesViewModel.handleEvents(FavoritesMovieEvent.AddToFavorites(movie))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}