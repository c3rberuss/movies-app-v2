package xyz.c3rberuss.movies.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.R
import xyz.c3rberuss.movies.databinding.PosterItemBinding
import xyz.c3rberuss.movies.postersDiffUtils

class PopularMoviesPaginatedAdapter(
    private val baseImageUrl: String,
    private val onOpenMovie: (poster: Poster) -> Unit
) : PagingDataAdapter<Poster, PopularMoviesPaginatedAdapter.PosterViewHolder>(postersDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            PosterViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class PosterViewHolder(private val binding: PosterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Poster) {
            with(binding) {
                movieTitle.text = data.title
                releaseDate.text = data.date

                votesAverage.setPercentage((data.score * 10).toInt())
                status.isVisible = data.isFavorite != data.isNoFavorite

                if (data.isFavorite && !data.isNoFavorite) {
                    status.setImageResource(R.drawable.baseline_thumb_up_black_36dp)
                } else if (!data.isFavorite && data.isNoFavorite) {
                    status.setImageResource(R.drawable.baseline_thumb_down_black_36dp)
                }

                Glide.with(root)
                    .load("$baseImageUrl${data.image}")
                    .into(posterImage)

                root.setOnClickListener {
                    onOpenMovie(data)
                }
            }
        }
    }
}