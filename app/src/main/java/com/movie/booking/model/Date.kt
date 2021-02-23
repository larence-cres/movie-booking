package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Date() : Parcelable {

    var date: String? = null
    var day: String? = null
    var month: String? = null
    var dayOfWeek: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Date> {
        override fun createFromParcel(parcel: Parcel): Date {
            return Date(parcel)
        }

        override fun newArray(size: Int): Array<Date?> {
            return arrayOfNulls(size)
        }
    }

}