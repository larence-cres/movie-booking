package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class BookedMovie() : Parcelable {

    var movieId: Long = 0

    var movieName = ""

    var ticketId = ""

    var date = ""

    var time = ""

    var seat = ""

    var price = 0.0

    var posterPath = ""

    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<BookedMovie> {
        override fun createFromParcel(parcel: Parcel): BookedMovie {
            return BookedMovie(parcel)
        }

        override fun newArray(size: Int): Array<BookedMovie?> {
            return arrayOfNulls(size)
        }
    }

}