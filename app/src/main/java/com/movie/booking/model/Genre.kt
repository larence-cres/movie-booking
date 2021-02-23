package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Genre() : Parcelable {

    @SerializedName("id")
    val id: Long = 0

    @SerializedName("name")
    val name: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Genre> {
        override fun createFromParcel(parcel: Parcel): Genre {
            return Genre(parcel)
        }

        override fun newArray(size: Int): Array<Genre?> {
            return arrayOfNulls(size)
        }
    }

}