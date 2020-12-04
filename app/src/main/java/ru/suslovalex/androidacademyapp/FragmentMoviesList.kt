package ru.suslovalex.androidacademyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.suslovalex.androidacademyapp.adapters.AdapterMovies
import ru.suslovalex.androidacademyapp.adapters.MovieItemClickListener
import ru.suslovalex.androidacademyapp.repository.getListMovies

class FragmentMoviesList: Fragment() {

    private lateinit var adapterMovies: AdapterMovies

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView = view.findViewById(R.id.rv_moviesList)
        adapterMovies = AdapterMovies(clickListener)
        recycler.layoutManager = GridLayoutManager(view.context,2)
        recycler.adapter = adapterMovies
        recycler.hasFixedSize()
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        adapterMovies.setMovies(getListMovies())
        adapterMovies.notifyDataSetChanged()
    }

    private val clickListener = object: MovieItemClickListener {
        override fun onClick(id: Int) {
            doOnClick(id)
        }
    }

    private fun doOnClick(id: Int) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.container_layout, FragmentMoviesDetails.newInstance(id))
                .addToBackStack(null)
                .commit()
        }

    }
}