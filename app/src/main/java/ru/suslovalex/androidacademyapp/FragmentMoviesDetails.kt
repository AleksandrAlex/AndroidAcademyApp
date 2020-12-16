package ru.suslovalex.androidacademyapp

import android.os.Bundle
import android.util.Log
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

    private lateinit var currentMovie: Movie
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
        val  actors = currentMovie.actors
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
        // When we receive a data we will put it to image
        val image: ImageView = view.findViewById(R.id.imageMovie)
        val adult: TextView = view.findViewById(R.id.adult_field)
        val title: TextView = view.findViewById(R.id.txt_title)
        val genre: TextView = view.findViewById(R.id.txt_genre)
        val rating: RatingBar = view.findViewById(R.id.rating_movie)
        val reviewers: TextView = view.findViewById(R.id.amount_review)
        val story: TextView = view.findViewById(R.id.story_text)
        val cast:TextView = view.findViewById(R.id.txt_cast)

       


        Glide.with(view).load(currentMovie.backdrop).into(image)
        adult.text = getAgeForLabel(currentMovie)
        title.text = currentMovie.title
        genre.text = getGenresForLabel(currentMovie)
        rating.rating = getRatingForLabel(currentMovie)
        reviewers.text = getRevForLabel(currentMovie)
        story.text = currentMovie.overview
//        hasActors(view)
        val actors = currentMovie.actors
//        Log.d("Actors:", "$actors")
        if (actors.isEmpty()){
            cast.visibility = View.GONE
        }


    }

//    private fun hasActors(view: View) {
//        if (currentMovie.actors[0]==null ){
//
//        }
//    }

    private fun getMovie() {
        currentMovie = arguments?.getParcelable("movie")!!
    }

    private fun getAgeForLabel(movie: Movie): String {
        return "${movie.minimumAge}+"
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