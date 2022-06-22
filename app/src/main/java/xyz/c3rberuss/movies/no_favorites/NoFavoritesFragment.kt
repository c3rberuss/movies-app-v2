package xyz.c3rberuss.movies.no_favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.MoviesAdapter
import xyz.c3rberuss.movies.R
import xyz.c3rberuss.movies.databinding.FragmentNoFavoritesBinding
import xyz.c3rberuss.movies.requestmanager.TheMovieDbApi

@AndroidEntryPoint
class NoFavoritesFragment : Fragment(R.layout.fragment_no_favorites) {

    private val noFavoritesViewModel: NoFavoritesViewModel by viewModels()
    private var _adapter: MoviesAdapter? = null
    private val adapter: MoviesAdapter get() = _adapter!!
    private val binding: FragmentNoFavoritesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = MoviesAdapter(
            baseImageUrl = TheMovieDbApi.IMAGES_URL,
            onOpenMovie = this::onOpenMovie
        )

        binding.noFavoritesMoviesList.adapter = adapter

        noFavoritesViewModel.noFavoritesMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            setupUi(it)
        }
    }

    //Muestra u oculta elementos de la interfaz segun el caso
    private fun setupUi(movies: List<Poster>) {
        if (movies.isEmpty()) {
            binding.emptyNonFavorites.root.visibility = View.VISIBLE
            binding.emptyNonFavorites.emptyText.text =
                resources.getString(R.string.empty_non_favorites)
            binding.progressBarNoFavorites.visibility = View.GONE
            binding.noFavoritesMoviesList.visibility = View.GONE
        } else {
            binding.progressBarNoFavorites.visibility = View.GONE
            binding.noFavoritesMoviesList.visibility = View.VISIBLE
            binding.emptyNonFavorites.root.visibility = View.GONE
        }
    }

    private fun onOpenMovie(movie: Poster) {
        val action =
            NoFavoritesFragmentDirections.actionNavigationNoFavoritesToMovieDetailActivity(
                movieId = movie.id,
                isNofavorite = true
            )
        findNavController().navigate(action)
    }
}