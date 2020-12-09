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
import ru.suslovalex.androidacademyapp.model.Movie

class AdapterMovies(private val adapterOnClick: (Int) -> Unit, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener {
            adapterOnClick(movies[position].id)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.pic_movie)
    private val adult: TextView = itemView.findViewById(R.id.txt_age)

    //    private val like:
    private val genre: TextView = itemView.findViewById(R.id.txt_genre)
    private val rating: RatingBar = itemView.findViewById(R.id.rating_movie)
    private val reviewers: TextView = itemView.findViewById(R.id.amount_review)
    private val title: TextView = itemView.findViewById(R.id.txt_title)
    private val timeLimit: TextView = itemView.findViewById(R.id.time_limit)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context).load(movie.image).into(image)
        adult.text = movie.adult.toString()
        genre.text = movie.genre
        rating.rating = movie.rating
        reviewers.text = movie.reviewers.toString()
        title.text = movie.title
        timeLimit.text = movie.timeLimit.toString()
    }
}

interface MovieItemClickListener {
    fun onClick(id: Int)
}