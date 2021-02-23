package com.movie.booking.activity.home.mvp

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jakewharton.rxbinding2.view.RxView
import com.movie.booking.R
import com.movie.booking.adapter.DateAdapter
import com.movie.booking.adapter.MovieAdapter
import com.movie.booking.api.ApiUrls
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.Utils
import com.movie.booking.dialog.ticketDialog.TicketDialog
import com.movie.booking.model.BookedMovie
import com.movie.booking.model.Date
import com.movie.booking.model.Genre
import com.movie.booking.model.Movie
import com.movie.booking.widget.ZoomOutPageTransformer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.detail_bottom_sheet.view.*
import org.json.JSONArray
import timber.log.Timber


/**
 * Created by Larence on 10/29/2020.
 */

class HomeView(
    private val appCompatActivity: AppCompatActivity,
    private val movieAdapter: MovieAdapter,
    private val dateAdapter: DateAdapter,
    private val ticketDialog: TicketDialog,
) : FrameLayout(appCompatActivity) {

    //Defining a BottomSheetBehavior instance
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    // Init data
    private var selectedDate = ""
    private var selectedTime = ""
    private var selectedMovie = Movie()
    private var bookedMovie = false
    private var toBroadcast = false

    private var genres = ArrayList<Genre>()
    private var movies = ArrayList<Movie>()
    private var bookedMovies = ArrayList<Long>()

    /**
     * Instantiate the layout
     */
    init {
        View.inflate(appCompatActivity, R.layout.activity_home, this)
        appCompatActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        appCompatActivity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * Sets data
     *
     * @param movies
     * @param genres
     * @param bookedMovies
     */
    fun setData(
        movies: ArrayList<Movie>,
        genres: ArrayList<Genre>,
        bookedMovies: ArrayList<Long>,
    ) {
        this.movies = movies
        this.genres = genres
        this.bookedMovies = bookedMovies
    }

    /**
     * Returns selected date
     *
     * @return
     */
    fun getSelectedDate(): String {
        return selectedDate
    }

    /**
     * Sets selected date
     *
     * @param date
     */
    fun setSelectedDate(date: String) {
        selectedDate = date
    }

    /**
     * Returns selected time
     *
     * @return
     */
    fun getSelectedTime(): String {
        return selectedTime
    }

    /**
     * Returns selected movie
     *
     * @return
     */
    fun getSelectedMovie(): Movie {
        return selectedMovie
    }

    /**
     * Returns true if movie is booked
     *
     * @return
     */
    fun isBookedMovie(): Boolean {
        return bookedMovie
    }

    /**
     * Returns true if broadcast is needed
     *
     * @return
     */
    fun toBroadcast(): Boolean {
        return toBroadcast
    }

    /**
     * Set if broadcast condition is required
     *
     * @param set
     */
    fun setToBroadcast(set: Boolean) {
        toBroadcast = set
    }

    /**
     * Initialize view pager
     *
     */
    fun initViewPager() {
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())

        updateBottomSheet(movies[0])

        // Sets adapter
        movieAdapter.setMovies(movies)
        viewPager.adapter = movieAdapter

        // Listening to page changes of ViewPager and updates BottomSheet accordingly
        val pageListener: OnPageChangeListener = object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                updateBottomSheet(movies[position])
            }

            override fun onPageScrollStateChanged(state: Int) {}
        }
        viewPager.addOnPageChangeListener(pageListener)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
    }

    /**
     * Listens to state changes of BottomSheet
     *
     */
    fun bottomSheetStateChange() {
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                when {
                    toBroadcast() -> setViewIntent(1 - slideOffset)
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {}
        })
    }

    /**
     * Sends broadcast
     *
     * @param offset
     */
    fun setViewIntent(offset: Float) {
        val intent = Intent()
        intent.putExtra("offset", offset)
        intent.action = "viewIntent"
        appCompatActivity.sendBroadcast(intent)
    }

    /**
     * Updates BottomSheet data with the selected movie
     *
     * @param movie
     */
    fun updateBottomSheet(movie: Movie) {
        tvMovieName.text = movie.title
        tvRelease.text = Utils.simpleDate(movie.releaseDate!!)
        tvOverview.text = movie.overview
        dateAdapter.clearSelection()
        timesLayout.clearCheck()
        timesLayout2.clearCheck()

        var genreNames = ""
        var count = 0
        for (genre in genres) {
            if (movie.genreIds!!.contains(genre.id)) {
                when {
                    count > 0 -> {
                        genreNames += ", "
                    }
                }
                genreNames += genre.name
                count++
            }
        }
        tvGenre.text = "Genre: $genreNames"

        Picasso.get()
            .load(ApiUrls.IMAGE_PATH + movie.posterPath)
            .transform(RoundedCornersTransformation(120, 0))
            .placeholder(R.drawable.img_gallery)
            .error(R.drawable.img_gallery)
            .into(ivPoster, object : Callback {
                override fun onSuccess() {}
                override fun onError(exception: Exception?) {
                    Timber.e("onError : ${exception!!.localizedMessage}")
                }
            })

        val rating = String.format("%.1f", movie.averageVote / 2)
        tvRating.text = "Rating: $rating"

        when {
            bookedMovies.contains(movie.id) -> {
                bookedMovie = true
                btnBook.text = "View Ticket"
                btnBook.isEnabled = true
                clSelection.visibility = View.GONE
            }
            else -> {
                bookedMovie = false
                btnBook.text = "Book Seats"
                btnBook.isEnabled = false
                clSelection.visibility = View.VISIBLE
            }
        }

        selectedMovie = movie
    }

    /**
     * Calendar setup from current date to next 10 days
     *
     * @param dateForNext10Days
     */
    fun calendarSetup(dateForNext10Days: ArrayList<Date>) {
        val layoutManager =
            LinearLayoutManager(appCompatActivity, LinearLayoutManager.HORIZONTAL, false)
        rvCalendar.setHasFixedSize(true)
        rvCalendar.layoutManager = layoutManager
        dateAdapter.setDate(dateForNext10Days)
        rvCalendar.adapter = dateAdapter
    }

    /**
     * Observes date click from calendar
     *
     * @return observable from the date model
     */
    fun dateClickObservable(): Observable<Date> {
        return dateAdapter.getClickDetailObservable()
    }

    /**
     * Updates view and data for the selected date
     *
     * @param selectedDate
     */
    fun toggleDateSelection(selectedDate: Date) {
        dateAdapter.updateSelection(selectedDate)
    }

    /**
     * Listens time buttons click
     * TODO :: Need to update
     *
     */
    fun updateRadioGroup() {
        time1.setOnCheckedChangeListener { buttonView, isChecked ->
            timesLayout2.clearCheck()
            when {
                isChecked -> {
                    selectedTime = buttonView.text.toString()
                    enableBookSeatBtn(selectedTime.isNotEmpty())
                }
            }
        }
        time2.setOnCheckedChangeListener { buttonView, isChecked ->
            timesLayout2.clearCheck()
            when {
                isChecked -> {
                    selectedTime = buttonView.text.toString()
                    enableBookSeatBtn(selectedTime.isNotEmpty())
                }
            }
        }
        time3.setOnCheckedChangeListener { buttonView, isChecked ->
            timesLayout2.clearCheck()
            when {
                isChecked -> {
                    selectedTime = buttonView.text.toString()
                    enableBookSeatBtn(selectedTime.isNotEmpty())
                }
            }
        }
        time4.setOnCheckedChangeListener { buttonView, isChecked ->
            timesLayout.clearCheck()
            when {
                isChecked -> {
                    selectedTime = buttonView.text.toString()
                    enableBookSeatBtn(selectedTime.isNotEmpty())
                }
            }
        }
        time5.setOnCheckedChangeListener { buttonView, isChecked ->
            timesLayout.clearCheck()
            when {
                isChecked -> {
                    selectedTime = buttonView.text.toString()
                    enableBookSeatBtn(selectedTime.isNotEmpty())
                }
            }
        }
        time6.setOnCheckedChangeListener { buttonView, isChecked ->
            timesLayout.clearCheck()
            when {
                isChecked -> {
                    selectedTime = buttonView.text.toString()
                    enableBookSeatBtn(selectedTime.isNotEmpty())
                }
            }
        }
    }

    /**
     * Sets the enable state of book button
     *
     * @param enable
     */
    fun enableBookSeatBtn(enable: Boolean) {
        btnBook.isEnabled = enable
    }

    /**
     * Observes book button
     *
     * @return
     */
    fun bookSeatObservable(): Observable<Any> {
        return RxView.clicks(btnBook)
    }

    fun showTicket(): Observable<Boolean> {
        return ticketDialog.showDialog()
    }

    fun setTicketData(ticketData: BookedMovie) {
        ticketDialog.setContent(ticketData)
    }

    fun dialogDismissListener(listener: DialogInterface.OnDismissListener) {
        ticketDialog.dialogDismissListener(listener)
    }

    fun expandBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun collapseBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun getBottomSheetState(): Int {
        return bottomSheetBehavior.state
    }
}
