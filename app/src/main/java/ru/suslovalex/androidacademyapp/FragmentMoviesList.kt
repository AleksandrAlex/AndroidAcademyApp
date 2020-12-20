package ru.suslovalex.androidacademyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.suslovalex.androidacademyapp.adapters.AdapterMovies
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.loadMovies


class FragmentMoviesList : Fragment() {

    private lateinit var adapterMovies: AdapterMovies
    private lateinit var movieList: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            getMovies()
            updateUI(view, movieList)
        }
    }

    private suspend fun getMovies() = withContext(Dispatchers.IO){
        movieList = loadMovies(this@FragmentMoviesList.requireContext())
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