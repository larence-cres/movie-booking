package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class MovieDAO() : Parcelable {

    @SerializedName("page")
    val page: Int = 0

    @SerializedName("results")
    val results: ArrayList<Movie>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDAO> {
        override fun createFromParcel(parcel: Parcel): MovieDAO {
            return MovieDAO(parcel)
        }

        override fun newArray(size: Int): Array<MovieDAO?> {
            return arrayOfNulls(size)
        }
    }

}