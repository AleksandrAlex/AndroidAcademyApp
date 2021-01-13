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
import ru.suslovalex.androidacademyapp.data.MoviesResponse
import ru.suslovalex.androidacademyapp.data.Result


class AdapterMovies(private val adapterOnClick: (Result) -> Unit, private val moviesResponse: MoviesResponse) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(moviesResponse.results[position])
        holder.itemView.setOnClickListener {
            adapterOnClick(moviesResponse.results[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesResponse.results.size
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

    fun onBind(movie: Result) {
        val startImagePath = "https://image.tmdb.org/t/p/w500"
        val endImagePath = movie.posterPath
        val path = startImagePath+endImagePath

        Glide.with(itemView.context).load(path).into(image)
        adult.text = getAgeForLabel(movie)
        genre.text = getGenresForLabel(movie)
        rating.rating = getRatingForLabel(movie)
        reviewers.text = getRevForLabel(movie)
        title.text = movie.title
        timeLimit.text = getReleaseDateToLabel(movie)
    }

    private fun getReleaseDateToLabel(movie: Result): String {
        return movie.releaseDate
    }

    private fun getRevForLabel(movie: Result): String {
        return "${movie.voteCount} Reviews"
    }

    private fun getRatingForLabel(movie: Result): Float {
        val voteAverage = movie.voteAverage / 2
        return voteAverage.toFloat()
    }

    private fun getGenresForLabel(movie: Result): String {
       return movie.genreIds.map { "$it," }.toString()
    }

    private fun getAgeForLabel(movie: Result): String {
        return movie.adult.toString()
    }
}
