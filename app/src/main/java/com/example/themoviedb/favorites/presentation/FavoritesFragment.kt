package com.example.themoviedb.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.common.data.MovieRepositoryImp
import com.example.themoviedb.common.utils.DefaultDispatcher
import com.example.themoviedb.databinding.FragmentFavoritesBinding
import com.example.themoviedb.favorites.domain.GetFavoriteMovies
import com.example.themoviedb.home.domain.FavoriteMoviesUseCase
import com.example.themoviedb.home.presentation.HomeFragment
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MovieRepositoryImp(
                    HomeFragment.getMovieApi(requireContext()),
                    HomeFragment.getMovieDatabase(requireContext())
                )
                return FavoritesViewModel(
                    DefaultDispatcher(), GetFavoriteMovies(repository),
                    FavoriteMoviesUseCase(repository)
                ) as T
            }
        }
        favoritesViewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteMovies.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesViewModel.favoriteMovies.collect {
                    binding.favoriteMovies.adapter = FavoriteMoviesAdapter(it, ::addToFavorites)
                }
            }
        }
    }

    private fun addToFavorites(id: Int, isFavorite: Boolean) {
        favoritesViewModel.addToFavorites(id, isFavorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}