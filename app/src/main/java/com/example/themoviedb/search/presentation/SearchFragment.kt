package com.example.themoviedb.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.common.domain.entities.Movie
import com.example.themoviedb.databinding.FragmentSearchBinding
import com.example.themoviedb.favorites.presentation.FavoriteMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchViewMovie.setOnQueryTextListener(this)
        binding.searchViewMovie.setOnCloseListener(this)
        binding.favoriteMovies.layoutManager = LinearLayoutManager(requireContext())
        subscribeToUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchViewModel.handleEvents(SearchMovieEvent.QueryInput(newText))
        }
        return false
    }

    override fun onClose(): Boolean {
        binding.searchViewMovie.clearFocus()
        searchViewModel.handleEvents(SearchMovieEvent.OnTextCleared)
        return true
    }

    private fun subscribeToUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.state.collect {
                    when (it) {
                        is SearchMovieUiState.Error -> {
                            setUpErrorState()
                            showErrorDialog(it.message)
                        }
                        SearchMovieUiState.Loading -> {
                            binding.searchProgressBar.isVisible = true
                            binding.initialSearchText.isVisible = false
                            binding.initialSearchImageView.isVisible = false
                        }
                        SearchMovieUiState.NoResultFound -> {
                            setUpNoResultState()
                        }
                        is SearchMovieUiState.Success -> {
                            binding.searchProgressBar.isVisible = false
                            binding.favoriteMovies.isVisible = true
                            binding.favoriteMovies.adapter = FavoriteMoviesAdapter(it.data, ::onMovieClick, ::addToFavorites)
                        }
                        SearchMovieUiState.Initial -> {
                            setUpInitialState()
                        }
                    }
                }
            }
        }
    }

    private fun addToFavorites(movie: Movie) {
        searchViewModel.handleEvents(SearchMovieEvent.AddToFavorites(movie))
    }

    private fun onMovieClick(movie: Movie) {
        val action = SearchFragmentDirections.actionNavigationSearchToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }

    private fun setUpInitialState() {
        binding.initialSearchText.isVisible = true
        binding.initialSearchImageView.isVisible = true
        binding.searchProgressBar.isVisible = false
        binding.favoriteMovies.isVisible = false
        binding.initialSearchImageView.setImageResource(R.drawable.ic_search_24)
        binding.initialSearchText.text = requireContext().getText(R.string.initial_search_text_label)
    }

    private fun setUpErrorState() {
        binding.initialSearchText.isVisible = false
        binding.initialSearchImageView.isVisible = false
        binding.searchProgressBar.isVisible = false
        binding.favoriteMovies.isVisible = false
        binding.searchViewMovie.clearFocus()
    }

    private fun setUpNoResultState(){
        binding.initialSearchText.isVisible = true
        binding.initialSearchImageView.isVisible = true
        binding.searchProgressBar.isVisible = false
        binding.favoriteMovies.isVisible = false
        binding.initialSearchImageView.setImageResource(R.drawable.ic_search_off_24)
        binding.initialSearchText.text = requireContext().getText(R.string.no_result_found)
    }

    private fun showErrorDialog(error: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(error)
        builder.setCancelable(true)

        builder.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}