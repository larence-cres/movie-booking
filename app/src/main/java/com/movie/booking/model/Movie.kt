package com.movie.booking.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Movie() : Parcelable {

    @SerializedName("id")
    val id: Long = 0

    @SerializedName("original_title")
    val originalTitle: String? = null

    @SerializedName("overview")
    val overview: String? = null

    @SerializedName("release_date")
    val releaseDate: String? = null

    @SerializedName("poster_path")
    val posterPath: String? = null

    @SerializedName("popularity")
    val popularity = 0.0

    @SerializedName("title")
    val title: String? = null

    @SerializedName("vote_average")
    val averageVote = 0.0

    @SerializedName("vote_count")
    val voteCount: Long = 0

    @SerializedName("backdrop_path")
    val backdropPath: String? = null

    @SerializedName("genre_ids")
    val genreIds: ArrayList<Long>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}