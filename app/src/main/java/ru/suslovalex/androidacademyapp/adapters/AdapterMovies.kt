package ru.suslovalex.androidacademyapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.BASE_IMAGE_URL


class AdapterMovies(private val adapterOnClick: (Movie) -> Unit) :
    ListAdapter<Movie, MovieViewHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            adapterOnClick(getItem(position))
        }
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.pic_movie)
    private val adult: TextView = itemView.findViewById(R.id.txt_age)
    private val genre: TextView = itemView.findViewById(R.id.txt_genre)
    private val rating: RatingBar = itemView.findViewById(R.id.rating_movie)
    private val reviewers: TextView = itemView.findViewById(R.id.amount_review)
    private val title: TextView = itemView.findViewById(R.id.txt_title)
    private val timeLimit: TextView = itemView.findViewById(R.id.time_limit)

    fun onBind(movie: Movie) {

        val endImagePath = movie.posterPath
        val path = BASE_IMAGE_URL+endImagePath

        Glide.with(itemView.context).load(path).into(image)
        adult.text = getAgeForLabel(movie)
        genre.text = getGenresForLabel(movie)
        rating.rating = getRatingForLabel(movie)
        reviewers.text = getRevForLabel(movie)
        title.text = movie.title
        timeLimit.text = getRuntime(movie)
    }

    private fun getRuntime(movie: Movie): String {
        return "${movie.runtime} min"
    }

    private fun getRevForLabel(movie: Movie): String {
        return "${movie.voteCount} Reviews"
    }

    private fun getRatingForLabel(movie: Movie): Float {
        val voteAverage = movie.voteAverage / 2
        return voteAverage.toFloat()
    }

    private fun getGenresForLabel(movie: Movie): String {
        return movie.genres.map { it.name }.joinToString { it }
    }

    private fun getAgeForLabel(movie: Movie): String {
        return movie.adult
    }
}

class MovieDiffUtil: DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}
