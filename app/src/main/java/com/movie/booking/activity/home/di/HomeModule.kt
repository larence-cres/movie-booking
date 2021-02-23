package com.movie.booking.activity.home.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import com.movie.booking.activity.home.mvp.HomeModel
import com.movie.booking.activity.home.mvp.HomePresenter
import com.movie.booking.activity.home.mvp.HomeView
import com.movie.booking.adapter.DateAdapter
import com.movie.booking.adapter.MovieAdapter
import com.movie.booking.api.WebService
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.appUtils.SharedPreferenceUtils
import com.movie.booking.dialog.ticketDialog.TicketDialog
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
class HomeModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun homeView(
        movieAdapter: MovieAdapter,
        dateAdapter: DateAdapter,
        ticketDialog: TicketDialog
    ): HomeView {
        return HomeView(appCompatActivity, movieAdapter, dateAdapter, ticketDialog)
    }

    @AppActivity
    @Provides
    fun homePresenter(homeView: HomeView, homeModel: HomeModel): HomePresenter {
        return HomePresenter(homeView, homeModel)
    }

    @AppActivity
    @Provides
    fun homeModel(
        sharedPreferenceUtils: SharedPreferenceUtils
    ): HomeModel {
        return HomeModel(appCompatActivity, sharedPreferenceUtils)
    }

    @AppActivity
    @Provides
    fun movieAdapter(): MovieAdapter {
        return MovieAdapter(appCompatActivity.supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }

    @AppActivity
    @Provides
    fun dateAdapter(): DateAdapter {
        return DateAdapter(appCompatActivity)
    }

    @AppActivity
    @Provides
    fun ticketDialog(): TicketDialog {
        return TicketDialog(appCompatActivity)
    }

}