package com.movie.booking.activity.splashScreen.mvp

import android.annotation.SuppressLint
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.model.Genre
import com.movie.booking.model.GenreDAO
import com.movie.booking.model.MovieDAO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Larence on 10/29/2020.
 */

@SuppressLint("CheckResult")
class SplashScreenPresenter(
    private val splashScreenView: SplashScreenView,
    private val splashScreenModel: SplashScreenModel,
) {

    // Init data
    private var mCompositeDisposable = CompositeDisposable()
    private var genres = ArrayList<Genre>()

    fun onCreate() {
        splashScreenView.setLogo()
        when {
            splashScreenView.checkIfNetworkAvailable() -> getGenre()
            else -> run()
        }
    }

    /**
     * Runs splash screen for certain period of time
     */
    private fun run() {
        Timer("SplashPause", false).schedule(object : TimerTask() {
            override fun run() {
                splashScreenModel.startHomeActivity()
            }
        }, 1500)
//        val splashTread = object : Thread() {
//            override fun run() {
//                try {
//                    var waited = 0
//                    // Splash screen pause time
//                    while (waited < 1500) {
//                        sleep(100)
//                        waited += 100
//                    }
//                    splashScreenModel.startHomeActivity()
//                } catch (e: InterruptedException) {
//                    // do nothing
//                } finally {
//                    splashScreenModel.finishActivity()
//                }
//
//            }
//        }
//        splashTread.start()
    }

    /**
     * Fetch genres from the server
     */
    private fun getGenre() {
        mCompositeDisposable.add(
            splashScreenModel.getGenre()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onGenreRequestSuccess, this::onRequestError)
        )
    }

    /**
     * Processes the success response from the api
     * @param genre a DTO that holds data of list of genres
     */
    private fun onGenreRequestSuccess(genre: GenreDAO) {
        Timber.e("Success : ${genre.genres}")
        splashScreenModel.saveGenres(genre.genres!!)
        this.genres = genre.genres
        getMovies()
    }

    /**
     * Fetch movies from the server
     */
    private fun getMovies() {
        mCompositeDisposable.add(
            splashScreenModel.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onRequestSuccess, this::onRequestError)
        )
    }

    /**
     * Processes the success response from the api
     * @param movie a DTO that holds data of list of movies
     */
    private fun onRequestSuccess(movie: MovieDAO) {
        Timber.e("Success : ${movie.results}")
        splashScreenModel.saveMovies(movie.results!!)
        splashScreenModel.startHomeActivity()
    }

    /**
     * Processes the error response from the api
     * @param throwable the base class for the errors and exceptions
     */
    private fun onRequestError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                val responseBody = throwable.response().errorBody()
                Timber.e("HttpException : $responseBody")
            }
            is SocketTimeoutException -> {
                Timber.e("SocketTimeoutException : ${AppConstants.TIME_OUT}")
            }
            is IOException -> {
                Timber.e("IOException : ${AppConstants.NO_NETWORK}")
            }
        }
    }

    fun onDestroy() {
        mCompositeDisposable.clear()
    }
}