package ru.suslovalex.androidacademyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.suslovalex.androidacademyapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            initFragmentMoviesList()
        }
    }

    private fun initFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container_layout, FragmentMoviesList())
            .commit()
    }
}