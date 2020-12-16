package ru.suslovalex.androidacademyapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.data.Movie


class AdapterMovies(private val adapterOnClick: (Movie) -> Unit, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener {
            adapterOnClick(movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
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
        Glide.with(itemView.context).load(movie.poster).into(image)
        adult.text = getAgeForLabel(movie)
        genre.text = getGenresForLabel(movie)
        rating.rating = getRatingForLabel(movie)
        reviewers.text = getRevForLabel(movie)
        title.text = movie.title
        timeLimit.text = getTimeForLabel(movie)
    }

    private fun getTimeForLabel(movie: Movie): String {
        return "${movie.runtime} min"
    }

    private fun getRevForLabel(movie: Movie): String {
        return "${movie.numberOfRatings} Reviews"
    }

    private fun getRatingForLabel(movie: Movie): Float {
        return movie.ratings / 2
    }

    private fun getGenresForLabel(movie: Movie): String {
        val genres = movie.genres.map { it.name }.toString()
        return genres.subSequence(1, genres.length - 1).toString()
    }

    private fun getAgeForLabel(movie: Movie): String {
        return "${movie.minimumAge}+"
    }
}
