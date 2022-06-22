package xyz.c3rberuss.movies.popular

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.R
import xyz.c3rberuss.movies.databinding.FragmentPopularBinding
import xyz.c3rberuss.movies.requestmanager.TheMovieDbApi

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {

    private val popularViewModel: PopularViewModel by viewModels()
    private val binding: FragmentPopularBinding by viewBinding()

    private var _adapter: PopularMoviesPaginatedAdapter? = null
    private val adapter: PopularMoviesPaginatedAdapter get() = _adapter!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = PopularMoviesPaginatedAdapter(
            baseImageUrl = TheMovieDbApi.IMAGES_URL,
            onOpenMovie = this::onOpenMovie
        )

        binding.popularMoviesList.adapter = adapter
        binding.error.btnTry.setOnClickListener {
            adapter.refresh()
        }

        popularViewModel.popularMovies.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

        adapter.addLoadStateListener { state ->
            binding.error.root.isVisible = state.refresh is LoadState.Error
            binding.progressBar.isVisible = state.refresh is LoadState.Loading
        }
    }

    private fun onOpenMovie(poster: Poster) {
        val action =
            PopularFragmentDirections.actionNavigationPopularToMovieDetailActivity(
                movieId = poster.id,
                isFavorite = poster.isFavorite,
                isNofavorite = poster.isNoFavorite
            )

        findNavController().navigate(action)
    }
}