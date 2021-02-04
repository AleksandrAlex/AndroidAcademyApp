package ru.suslovalex.androidacademyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movies_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.adapters.AdapterActors
import ru.suslovalex.androidacademyapp.data.Actor
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.BASE_IMAGE_URL
import ru.suslovalex.androidacademyapp.viewmodel.*


class FragmentMoviesDetails : Fragment() {

    private lateinit var adapterActors: AdapterActors
    private val moviesDetailsViewModel: MoviesDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareActorsRecyclerView(view)

        arguments?.let { moviesDetailsViewModel.getMovie(it) }
        moviesDetailsViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is MoviesDetailsState.Success -> {
                    setupUI(view, state.movie)
                    updateActorRecyclerView(state.movie.actors)
                    hideProgressbar()
                }
                is MoviesDetailsState.Error -> showError(state.errorMessage)
                is MoviesDetailsState.Loading -> showProgressbar()
            }
        })
    }

    private fun prepareActorsRecyclerView(view: View) {
        adapterActors = AdapterActors()
        val actorRecyclerView: RecyclerView = view.findViewById(R.id.rv_actorList)
        actorRecyclerView.adapter = adapterActors
    }

    private fun updateActorRecyclerView(actors: List<Actor>) {
        adapterActors.submitList(actors)
    }

    private fun setupUI(view: View, currentMovie: Movie) {
        initViews(view, currentMovie)
        initBackBottom(view)
    }

    private fun initBackBottom(view: View) {
        view.findViewById<TextView>(R.id.back).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun initViews(view: View, currentMovie: Movie) {
        val image: ImageView = view.findViewById(R.id.imageMovie)
        val adult: TextView = view.findViewById(R.id.adult_field)
        val title: TextView = view.findViewById(R.id.txt_title)
        val genre: TextView = view.findViewById(R.id.txt_genre)
        val rating: RatingBar = view.findViewById(R.id.rating_movie)
        val reviewers: TextView = view.findViewById(R.id.amount_review)
        val story: TextView = view.findViewById(R.id.story_text)
        val cast: TextView = view.findViewById(R.id.txt_cast)

        if (currentMovie.actors.isEmpty()) {
            cast.visibility = View.GONE
        }

        val endImagePath = currentMovie.backdropPath
        val path = BASE_IMAGE_URL + endImagePath

        currentMovie.let {
            Glide.with(view).load(path).into(image)
            adult.text = getAgeForLabel(it)
            title.text = it.title
            genre.text = getGenresForLabel(it)
            rating.rating = getRatingForLabel(it)
            reviewers.text = getRevForLabel(it)
            story.text = it.overview
        }
    }

    private fun getAgeForLabel(movie: Movie): String {
        return movie.adult
    }

    private fun getRevForLabel(movie: Movie): String {
        return "${movie.voteCount} Reviews"
    }

    private fun getRatingForLabel(movie: Movie): Float {
        val rating = movie.voteAverage.toFloat()
        return rating / 2
    }

    private fun getGenresForLabel(movie: Movie): String {
        return movie.genres.map { it.name }.joinToString { it }
    }

    private fun hideProgressbar() {
        progress_bar_detailsMovie?.visibility = View.INVISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showProgressbar() {
        progress_bar_detailsMovie?.visibility = View.VISIBLE
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