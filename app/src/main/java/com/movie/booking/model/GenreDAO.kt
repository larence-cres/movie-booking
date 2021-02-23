package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class GenreDAO() : Parcelable {

    @SerializedName("genres")
    val genres: ArrayList<Genre>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenreDAO> {
        override fun createFromParcel(parcel: Parcel): GenreDAO {
            return GenreDAO(parcel)
        }

        override fun newArray(size: Int): Array<GenreDAO?> {
            return arrayOfNulls(size)
        }
    }

}