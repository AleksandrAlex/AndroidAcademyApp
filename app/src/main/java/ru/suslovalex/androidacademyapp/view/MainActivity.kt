package ru.suslovalex.androidacademyapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import ru.suslovalex.androidacademyapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            initFragmentMoviesList()
            handleIntent(intent)
        }
    }

    private fun initFragmentMoviesList() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container_layout, FragmentMoviesList())
            .commit()
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                if (id != null) {
                    openFragmentMovieDetails(id)
                }
            }
        }
    }

    private fun openFragmentMovieDetails(id: Long) {
        supportFragmentManager.popBackStack()
           supportFragmentManager.beginTransaction()
               .replace(R.id.container_layout, FragmentMoviesDetails.newInstance(id))
               .addToBackStack(null)
               .commit()
        Toast.makeText(applicationContext, "$id", Toast.LENGTH_LONG).show()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent!=null){
            handleIntent(intent)
        }
    }
}