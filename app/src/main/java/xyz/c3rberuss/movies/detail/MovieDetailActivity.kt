package xyz.c3rberuss.movies.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import xyz.c3rberuss.domain.Movie
import xyz.c3rberuss.domain.Resource
import xyz.c3rberuss.movies.R
import xyz.c3rberuss.movies.databinding.ActivityMovieDetailBinding
import xyz.c3rberuss.movies.image_view.ImageViewActivity
import xyz.c3rberuss.movies.requestmanager.TheMovieDbApi

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(R.layout.activity_movie_detail) {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val binding: ActivityMovieDetailBinding by viewBinding()
    private val args: MovieDetailActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewModel.fetchMovieDetail(
            movieId = args.movieId,
            isFavoriteMovie = args.isFavorite,
            isNoFavoriteMovie = args.isNofavorite
        )

        viewModel.status.observe(this) {
            binding.loadingDetail.isVisible = it is Resource.Loading
            binding.containerDetail.isVisible = it is Resource.Success
        }

        viewModel.movie.observe(this) { movie ->
            movie?.let { loadData(it) }
        }


        viewModel.isFavorite.observe(this) {

            if (it && !viewModel.isNoFavorite.value!!) {
                binding.btnFavorite.isEnabled = true
                binding.btnNoFavorite.isEnabled = false

                binding.btnFavorite.text = resources.getText(R.string.remove_from_favorite_list)
                binding.btnFavorite.setOnClickListener {
                    viewModel.removeFromFavorite()
                    showModal(false)
                }
            } else if (!it && viewModel.isNoFavorite.value!!) {

                binding.btnFavorite.isEnabled = false
                binding.btnNoFavorite.isEnabled = true

                binding.btnFavorite.text = resources.getText(R.string.add_to_favorite_list)

                binding.btnFavorite.setOnClickListener {
                    viewModel.addToFavorites()
                    showModal(true)
                }
            } else {
                binding.btnFavorite.text = resources.getText(R.string.add_to_favorite_list)
                binding.btnFavorite.isEnabled = true
                binding.btnNoFavorite.isEnabled = true

                binding.btnFavorite.setOnClickListener {
                    viewModel.addToFavorites()
                    showModal(true)
                }
            }

        }

        // Maneja el estado del boton Agregar a NO favoritos
        viewModel.isNoFavorite.observe(this) {

            if (it && !viewModel.isFavorite.value!!) {
                binding.btnNoFavorite.isEnabled = true
                binding.btnFavorite.isEnabled = false

                binding.btnNoFavorite.text = resources.getText(R.string.remove_from_no_favorites)
                binding.btnNoFavorite.setOnClickListener {
                    viewModel.removeFromNoFavorites()
                    showModal(false)
                }
            } else if (!it && viewModel.isFavorite.value!!) {

                binding.btnNoFavorite.isEnabled = false
                binding.btnFavorite.isEnabled = true

                binding.btnNoFavorite.text = resources.getText(R.string.add_to_no_favorite_list)

                binding.btnNoFavorite.setOnClickListener {
                    viewModel.addToNoFavorites()
                    showModal(true)
                }
            } else {
                binding.btnNoFavorite.text = resources.getText(R.string.add_to_no_favorite_list)
                binding.btnNoFavorite.isEnabled = true
                binding.btnFavorite.isEnabled = true

                binding.btnNoFavorite.setOnClickListener {
                    viewModel.addToNoFavorites()
                    showModal(true)
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Carga los datos de la película en la Vista
    private fun loadData(movie: Movie) {
        binding.movieTitleDetail.text = movie.title
        binding.movieOverview.text = movie.overview
        binding.tagline.text = movie.tag
        binding.movieGenres.text =
            movie.genres.joinToString { it.name }

        binding.votesAverageDetail.setPercentage((movie.score * 10).toInt())

        Glide.with(binding.root)
            .load(TheMovieDbApi.IMAGES_URL + movie.background)
            .into(binding.background)

        Glide.with(binding.root)
            .load(TheMovieDbApi.IMAGES_URL + movie.poster)
            .into(binding.moviePoster)

        binding.moviePoster.setOnClickListener {
            val intent = Intent(this, ImageViewActivity::class.java)
            intent.putExtra("url", TheMovieDbApi.IMAGES_URL + movie.poster)
            startActivity(intent)
        }
    }

    // Muestra un modal para notificar al usuario sobre la acción que se realizó
    private fun showModal(added: Boolean) {

        val titleDialog = if (added) R.string.dialog_title_add else R.string.dialog_title_remove
        val messageDialog = if (added) R.string.movie_added else R.string.movie_removed

        MaterialAlertDialogBuilder(this).apply {
            setTitle(titleDialog)
            setMessage(messageDialog)
            setPositiveButton(resources.getString(R.string.ok)) { _, _ ->

            }
            show()
        }
    }
}