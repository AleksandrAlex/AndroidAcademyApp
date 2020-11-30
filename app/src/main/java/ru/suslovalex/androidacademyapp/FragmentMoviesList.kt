package ru.suslovalex.androidacademyapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentMoviesList: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.item).setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.container_layout, FragmentMoviesDetails())
                ?.addToBackStack(null)?.commit()
        }
    }
}