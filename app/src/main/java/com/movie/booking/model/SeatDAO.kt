package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class SeatDAO() : Parcelable {

    @SerializedName("seats")
    val seats: ArrayList<Seat>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SeatDAO> {
        override fun createFromParcel(parcel: Parcel): SeatDAO {
            return SeatDAO(parcel)
        }

        override fun newArray(size: Int): Array<SeatDAO?> {
            return arrayOfNulls(size)
        }
    }

}