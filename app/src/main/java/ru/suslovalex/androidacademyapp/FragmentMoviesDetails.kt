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
import ru.suslovalex.androidacademyapp.adapters.AdapterActors
import ru.suslovalex.androidacademyapp.model.Movie
import ru.suslovalex.androidacademyapp.repository.getListActors
import ru.suslovalex.androidacademyapp.repository.getListMovies

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
        getMovieById()
        prepareAdapter()
        setupUI(view)
    }

    private fun prepareAdapter() {
        adapterActors = AdapterActors()
    }

    override fun onStart() {
        super.onStart()
        updateActorsRecyclerView()
    }

    private fun updateActorsRecyclerView() {
        adapterActors.setActors(getListActors())
        adapterActors.notifyDataSetChanged()
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
        // When we receive a data we will put it to story
        val story: TextView = view.findViewById(R.id.story_text)
        // We don't a have date
//        Glide.with(view).load(currentMovie.image).into(image)
        adult.text = currentMovie.adult.toString()
        title.text = currentMovie.title
        genre.text = currentMovie.genre
        rating.rating = currentMovie.rating
        reviewers.text = currentMovie.reviewers.toString()
        // We don't have date
//        story.text = "!!!Some story text!!!"
    }

    private fun getMovieById() {
        val id = arguments?.getInt("id")
        id?.let {
            currentMovie = getListMovies()[id]
        }
    }

    companion object {
        fun newInstance(id: Int): FragmentMoviesDetails {
            val args = Bundle()
            args.putInt("id", id)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}