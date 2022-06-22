package xyz.c3rberuss.movies.favorites

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
import xyz.c3rberuss.movies.databinding.FragmentFavoritesBinding
import xyz.c3rberuss.movies.requestmanager.TheMovieDbApi

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var _adapter: MoviesAdapter? = null
    private val adapter: MoviesAdapter get() = _adapter!!
    private val binding: FragmentFavoritesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = MoviesAdapter(
            baseImageUrl = TheMovieDbApi.IMAGES_URL,
            onOpenMovie = this::onOpenMovie
        )

        binding.favoritesMoviesList.adapter = adapter

        favoritesViewModel.favoritesMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            setupUi(it)
        }
    }

    //Muestra u oculta elementos de la interfaz segun el caso
    private fun setupUi(movies: List<Poster>) {

        if (movies.isEmpty()) {
            binding.emptyFavorites.root.visibility = View.VISIBLE
            binding.emptyFavorites.emptyText.text = resources.getString(R.string.empty_favorites)
            binding.progressBarFavorites.visibility = View.GONE
            binding.favoritesMoviesList.visibility = View.GONE
        } else {
            binding.progressBarFavorites.visibility = View.GONE
            binding.favoritesMoviesList.visibility = View.VISIBLE
            binding.emptyFavorites.root.visibility = View.GONE
        }
    }

    private fun onOpenMovie(movie: Poster) {
        val action =
            FavoritesFragmentDirections.actionNavigationFavoritesToMovieDetailActivity(
                movie.id,
                isFavorite = true
            )

        findNavController().navigate(action)
    }
}