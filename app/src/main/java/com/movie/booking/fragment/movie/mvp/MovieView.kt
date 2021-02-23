package com.movie.booking.fragment.movie.mvp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.movie.booking.R
import com.movie.booking.api.ApiUrls
import com.movie.booking.model.Genre
import com.movie.booking.model.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import jp.wasabeef.picasso.transformations.BlurTransformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.detail_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.ivPoster
import kotlinx.android.synthetic.main.fragment_movie.view.tvMovieName
import kotlinx.android.synthetic.main.fragment_movie.view.tvRating
import timber.log.Timber


/**
 * Created by Larence on 10/29/2020.
 */

class MovieView(
    private val appCompatActivity: AppCompatActivity,
) : FrameLayout(appCompatActivity) {

    private val genreViews = arrayOfNulls<TextView>(6)

    init {
        View.inflate(appCompatActivity, R.layout.fragment_movie, this)
    }

    fun initData(movie: Movie, genreList: ArrayList<Genre>) {
        genreViews[0] = genre1
        genreViews[1] = genre2
        genreViews[2] = genre3
        genreViews[3] = genre4
        genreViews[4] = genre5
        genreViews[5] = genre6

        tvMovieName.text = movie.title

        Picasso.get()
            .load(ApiUrls.IMAGE_PATH + movie.backdropPath)
            .transform(BlurTransformation(appCompatActivity, 25, 1))
            .placeholder(R.drawable.img_gallery)
            .error(R.drawable.img_gallery)
            .into(ivBackdrop, object : Callback {
                override fun onSuccess() {
//                    Timber.e("Image loading success")
                }

                override fun onError(exception: Exception?) {
                    Timber.e("onError : ${exception!!.localizedMessage}")
//                    Toast.makeText(appCompatActivity,
//                        "Cannot fetch data error",
//                        Toast.LENGTH_SHORT).show()
                }
            })

        Picasso.get()
            .load(ApiUrls.IMAGE_PATH + movie.posterPath)
            .transform(RoundedCornersTransformation(120, 0))
            .placeholder(R.drawable.img_gallery)
            .error(R.drawable.img_gallery)
            .into(ivPoster, object : Callback {
                override fun onSuccess() {
//                    Timber.e("Image loading success")
                }

                override fun onError(exception: Exception?) {
                    Timber.e("onError : ${exception!!.localizedMessage}")
//                    Toast.makeText(appCompatActivity,
//                        "Cannot fetch data error",
//                        Toast.LENGTH_SHORT).show()
                }
            })

        val rating = String.format("%.1f", movie.averageVote / 2)
        tvRating.text = rating
        ratingBar.rating = rating.toFloat()

        val genres = ArrayList<String>()
        for (genre in genreList) {
            if (movie.genreIds!!.contains(genre.id)) {
                genres.add(genre.name!!)
            }
        }
        for (i in 0 until genres.size) {
            genreViews[i]!!.visibility = VISIBLE
            genreViews[i]!!.text = genres[i].trim()
        }
        for (i in 5 downTo genres.size) {
            genreViews[i]!!.visibility = GONE
        }
    }

    fun posterBtnObservable(): Observable<Any> {
        return RxView.clicks(ivPoster)
    }

    fun registerReceiver() {
        appCompatActivity.registerReceiver(viewIntent, IntentFilter("viewIntent"))
    }

    fun unregisterReceiver() {
        appCompatActivity.unregisterReceiver(viewIntent)
    }

    private val viewIntent = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            setViewAlpha(intent.getFloatExtra("offset", -1F))
        }
    }

    private fun setViewAlpha(alpha: Float) {
        clView.alpha = alpha
    }

}