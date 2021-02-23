package com.movie.booking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.movie.booking.fragment.movie.MovieFragment
import com.movie.booking.model.Movie

class MovieAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    private var movies = ArrayList<Movie>()

    override fun getItem(position: Int): Fragment {
        val movieFragment = MovieFragment()
        movieFragment.newInstance(movies[position])
        return movieFragment
    }

    override fun getCount(): Int {
        return movies.size
    }

    fun setMovies(movies: ArrayList<Movie>){
        this.movies.clear()
        this.movies = movies
    }

}