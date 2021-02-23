package com.movie.booking.activity.splashScreen.mvp

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.movie.booking.R
import com.movie.booking.appUtils.Utils
import kotlinx.android.synthetic.main.activity_splash_screen.view.*

/**
 * Created by Larence on 10/29/2020.
 */

class SplashScreenView(
    private val appCompatActivity: AppCompatActivity,
) : FrameLayout(appCompatActivity) {

    /**
     * Instantiate the layout
     */
    init {
        appCompatActivity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        appCompatActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        View.inflate(appCompatActivity, R.layout.activity_splash_screen, this)
    }

    /**
     * Sets logo in the image view
     *
     */
    fun setLogo() {
        Picasso.get().load(R.drawable.clapperboard).into(ivLogo)
    }

    /**
     * Returns true if network is available
     */
    fun checkIfNetworkAvailable(): Boolean {
        return Utils.isNetworkAvailable(appCompatActivity)
    }

}