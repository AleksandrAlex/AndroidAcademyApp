package ru.suslovalex.androidacademyapp.domain

sealed class MovieResponseResult {
    object Success : MovieResponseResult()
    object Error : MovieResponseResult()
}