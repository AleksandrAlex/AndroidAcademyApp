package ru.suslovalex.androidacademyapp.repository

import ru.suslovalex.androidacademyapp.R
import ru.suslovalex.androidacademyapp.model.Actor
import ru.suslovalex.androidacademyapp.model.Movie

fun getListMovies(): List<Movie> {
    return listOf(
        Movie(
            0, R.drawable.img_color_movie, 13, false,
            "Action, Adventure, Drama", 4.0f, 125, "Avengers: End Game", 137
        ),
        Movie(
            1, R.drawable.img_tenet, 16, true,
            "Action, Sci-Fi, Thriller", 5.0f, 98, "Tenet", 97
        ),
        Movie(
           2, R.drawable.img_black_widow, 13, false,
            "Action, Adventure, Sci-Fi", 4.0f, 38, "Black Widow", 102
        ),
        Movie(
            3, R.drawable.img_ww84, 13, false,
            "Action, Adventure, Fantasy", 5.0f, 74, "Wonder Woman 1984", 120
        )
    )

}

fun getListActors(): List<Actor>{
    return listOf(
        Actor(
            0, R.drawable.img_downey, "Robert Downey Jr."
        ),
        Actor(
            1, R.drawable.img_evans, "Chris Evans"
        ),
        Actor(
            2, R.drawable.img_ruffalo, "Mark Ruffalo"
        ),
        Actor(
            3, R.drawable.img_hemsworth, "Chris Hemsworth"
        ),
        Actor(
            4, R.drawable.img_downey, "Robert Downey Jr."
        ),
        Actor(
            5, R.drawable.img_evans, "Chris Evans"
        ),
        Actor(
            6, R.drawable.img_ruffalo, "Mark Ruffalo"
        ),
        Actor(
            7, R.drawable.img_hemsworth, "Chris Hemsworth"
        )
    )
}