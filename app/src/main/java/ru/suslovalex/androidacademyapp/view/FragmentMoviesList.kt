package ru.suslovalex.androidacademyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.adapters.AdapterMovies
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.viewmodel.MoviesListViewModel


class FragmentMoviesList : Fragment() {

    private lateinit var adapterMovies: AdapterMovies
    private lateinit var moviesListViewModel: MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesListViewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
        lifecycleScope.launch {
            moviesListViewModel.getMovies(this@FragmentMoviesList.requireContext())
            moviesListViewModel.movieList.observe(this@FragmentMoviesList, Observer {
                updateUI(view, it)
            })
        }
    }

    private fun updateUI(view: View, movieList: List<Movie>) {
            val recycler: RecyclerView = view.findViewById(R.id.rv_moviesList)
            adapterMovies = AdapterMovies({ movie -> doOnClick(movie) }, movieList)
            recycler.layoutManager = GridLayoutManager(view.context, 2)
            recycler.adapter = adapterMovies
            recycler.hasFixedSize()
        }

    private fun doOnClick(movie: Movie) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.container_layout, FragmentMoviesDetails.newInstance(movie))
                .addToBackStack(null)
                .commit()
        }
    }
}