package com.movie.booking.fragment.movie.mvp

import android.annotation.SuppressLint
import com.movie.booking.model.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Larence on 10/29/2020.
 */

@SuppressLint("CheckResult")
class MoviePresenter(
    private val movieView: MovieView,
    private val movieModel: MovieModel,
) {

    private var mCompositeDisposable = CompositeDisposable()

    fun onCreateView(movie: Movie?) {
        movieView.initData(movie!!, movieModel.getGenres())
        movieObservable()
        movieView.registerReceiver()
    }

    private fun movieObservable() {
        movieView.posterBtnObservable().subscribe {
            movieModel.setBottomSheetIntent()
        }
    }

    fun onDestroyView() {
        mCompositeDisposable.clear()
        movieView.unregisterReceiver()
    }

}