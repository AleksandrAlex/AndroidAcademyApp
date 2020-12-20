package ru.suslovalex.androidacademyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.suslovalex.androidacademyapp.adapters.AdapterActors
import ru.suslovalex.androidacademyapp.data.Movie


class FragmentMoviesDetails : Fragment() {

    private  var currentMovie: Movie? = null
    private lateinit var adapterActors: AdapterActors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            getMovie()
            prepareAdapter()
            setupUI(view)
    }

    private fun prepareAdapter() {
        val  actors = currentMovie?.actors ?: arrayListOf()
        adapterActors = AdapterActors(actors)
    }

    private fun setupUI(view: View) {
        prepareViews(view)
        prepareActorRecyclerView(view)
        initBackBottom(view)
    }

    private fun initBackBottom(view: View) {
        view.findViewById<TextView>(R.id.back).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun prepareActorRecyclerView(view: View) {
        val actorRecyclerView: RecyclerView = view.findViewById(R.id.rv_actorList)
        actorRecyclerView.adapter = adapterActors
    }

    private fun prepareViews(view: View) {
        val image: ImageView = view.findViewById(R.id.imageMovie)
        val adult: TextView = view.findViewById(R.id.adult_field)
        val title: TextView = view.findViewById(R.id.txt_title)
        val genre: TextView = view.findViewById(R.id.txt_genre)
        val rating: RatingBar = view.findViewById(R.id.rating_movie)
        val reviewers: TextView = view.findViewById(R.id.amount_review)
        val story: TextView = view.findViewById(R.id.story_text)
        val cast:TextView = view.findViewById(R.id.txt_cast)

        currentMovie?.let {
            Glide.with(view).load(it.backdrop).into(image)
            adult.text = getAgeForLabel(it)
            title.text = it.title
            genre.text = getGenresForLabel(it)
            rating.rating = getRatingForLabel(it)
            reviewers.text = getRevForLabel(it)
            story.text = it.overview
            if (it.actors.isEmpty()){
                cast.visibility = View.GONE
            }
        }
    }

    private fun getMovie() {
            currentMovie = arguments?.getParcelable("movie")
    }

    private fun getAgeForLabel(movie: Movie): String {
        return "${movie.minimumAge}+"
    }

    private fun getRevForLabel(movie: Movie): String {
        return "${movie.numberOfRatings} Reviews"
    }

    private fun getRatingForLabel(movie: Movie): Float {
       return movie.ratings
    }

    private fun getGenresForLabel(movie: Movie): String {
        val genres = movie.genres.map { it.name }.toString()
        return genres.subSequence(1, genres.length - 1).toString()
    }

    companion object {
        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable("movie", movie)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}