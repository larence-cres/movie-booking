package com.movie.booking.activity.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.movie.booking.activity.home.di.DaggerHomeComponent
import com.movie.booking.activity.home.di.HomeModule
import com.movie.booking.activity.home.mvp.HomePresenter
import com.movie.booking.activity.home.mvp.HomeView
import com.movie.booking.app.MyApplication
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.model.Genre
import com.movie.booking.model.Movie
import java.util.*
import javax.inject.Inject

/**
 * Created by Larence on 10/29/2020.
 */

class HomeActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    @Inject
    lateinit var homeView: HomeView

    @Inject
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myApplication = MyApplication()
        DaggerHomeComponent.builder()
            .appComponent(myApplication.get(this).appComponent())
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
        setContentView(homeView)
        homePresenter.onCreate()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_CANCELED -> {
                return
            }
            else -> when (requestCode) {
                AppConstants.REQUEST_CODE -> {
                    homePresenter.showTicket()
                    homeView.setData(
                        homePresenter.getMovies(),
                        homePresenter.getGenres(),
                        homePresenter.getBookedMovies()
                    )
                    homeView.updateBottomSheet(homeView.getSelectedMovie())
                }
            }
        }
    }

    /**
     * Handles back press
     *
     */
    override fun onBackPressed() {
        when (homeView.getBottomSheetState()) {
            BottomSheetBehavior.STATE_EXPANDED -> homeView.collapseBottomSheet()
            BottomSheetBehavior.STATE_COLLAPSED -> when {
                doubleBackToExitPressedOnce -> {
                    super.onBackPressed()
                    return
                }
                else -> {
                    this.doubleBackToExitPressedOnce = true
                    Toast.makeText(
                        this,
                        "Please click BACK again to exit",
                        Toast.LENGTH_SHORT
                    ).show()
                    Timer("BackPress", false).schedule(object : TimerTask() {
                        override fun run() {
                            doubleBackToExitPressedOnce = false
                        }
                    }, 2000)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.onDestroy()
    }
}