package ru.suslovalex.androidacademyapp.domain

sealed class MovieResponseResult {
class Success: MovieResponseResult()
class Error: MovieResponseResult()
}