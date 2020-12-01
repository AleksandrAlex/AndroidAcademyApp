package ru.suslovalex.androidacademyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragmentMoviesList()
    }

    private fun initFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container_layout, FragmentMoviesList())
            .commit()
    }
}