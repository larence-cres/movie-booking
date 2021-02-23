package com.movie.booking.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.movie.booking.fragment.movie.mvp.MoviePresenter
import com.movie.booking.fragment.movie.mvp.MovieView
import javax.inject.Inject
import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.app.MyApplication
import com.movie.booking.fragment.movie.di.DaggerMovieComponent
import com.movie.booking.fragment.movie.di.MovieModule
import com.movie.booking.model.Movie

/**
 * Created by Larence on 10/29/2020.
 */

class MovieFragment : Fragment() {

    @Inject
    lateinit var movieView: MovieView

    @Inject
    lateinit var moviePresenter: MoviePresenter

    private var movie: Movie? = null

    fun newInstance(movie: Movie): MovieFragment {
        this.movie = movie
        return MovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        DaggerMovieComponent.builder()
            .appComponent(MyApplication().get(activity!!).appComponent())
            .movieModule(MovieModule(activity as AppCompatActivity))
            .build()
            .inject(this)

        moviePresenter.onCreateView(movie)
        return movieView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviePresenter.onDestroyView()
    }

}