package ru.suslovalex.androidacademyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies_list.*
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.adapters.AdapterMovies
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.Result
import ru.suslovalex.androidacademyapp.viewmodel.*


class FragmentMoviesList : Fragment() {

    private lateinit var adapterMovies: AdapterMovies
    private val moviesListViewModel: MoviesListViewModel by viewModels { MoviesListViewModelProviderFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            moviesListViewModel.moviesListState.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    is MoviesListState.Success -> {
                        hideProgressbar()
                        setupUI(view, state.movies)
                    }
                    is MoviesListState.Error ->
                        showError(state.errorMessage)

                    is MoviesListState.Loading ->
                        showProgressbar()
                }
            })
    }

    private fun hideProgressbar() {
        progress_bar.visibility = View.INVISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showProgressbar() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun setupUI(view: View, movies: List<Movie>) {
        val recycler: RecyclerView = view.findViewById(R.id.rv_moviesList)
        adapterMovies = AdapterMovies { movie -> doOnClick(movie) }
        recycler.layoutManager = GridLayoutManager(view.context, 2)
        recycler.adapter = adapterMovies
        adapterMovies.submitList(movies)
        adapterMovies.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun doOnClick(movie: Movie) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.container_layout, FragmentMoviesDetails.newInstance(movie))
//                .replace(R.id.container_layout, FragmentMoviesDetails())
                .addToBackStack(null)
                .commit()
        }
    }
}