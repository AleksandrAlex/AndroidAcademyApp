package ru.suslovalex.androidacademyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.adapters.AdapterMovies
import ru.suslovalex.androidacademyapp.data.Movie
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

        lifecycleScope.launch(Dispatchers.Main) {
            moviesListViewModel.getMovies(this@FragmentMoviesList.requireContext())
            moviesListViewModel.movieList.observe(viewLifecycleOwner, Observer {
                updateUI(view, it)
            })
            moviesListViewModel.state.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    is State.Loading -> showLoader()
                    is State.Error -> showError()
                    is State.Success -> {
                        hideLoader()
                    }
                }
            })
        }
    }

    private fun hideLoader() {
        Toast.makeText(this.requireContext(), "Hide Loader!", Toast.LENGTH_LONG).show()
    }

    private fun showError() {
        Toast.makeText(this.requireContext(), "Show Error!", Toast.LENGTH_LONG).show()
    }

    private fun showLoader() {
        Toast.makeText(this.requireContext(), "Show Loader!", Toast.LENGTH_LONG).show()
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