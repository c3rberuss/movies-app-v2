package xyz.c3rberuss.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.c3rberuss.domain.Poster
import xyz.c3rberuss.movies.databinding.PosterItemBinding

class MoviesAdapter(
    private val baseImageUrl: String,
    private val onOpenMovie: (poster: Poster) -> Unit
) : ListAdapter<Poster, MoviesAdapter.PosterViewHolder>(postersDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            PosterViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PosterViewHolder(private val binding: PosterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Poster) {
            with(binding) {
                movieTitle.text = data.title
                releaseDate.text = data.date

                votesAverage.setPercentage((data.score * 10).toInt())

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

val postersDiffUtils by lazy {
    object : DiffUtil.ItemCallback<Poster>() {
        override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Poster, newItem: Poster): Boolean {
            return oldItem == newItem
        }

    }
}