package com.movie.booking.adapter.seatBooking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.movie.booking.R
import com.movie.booking.model.Seat
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SeatBookingAdapter(private val mContext: Context) :
    SelectableAdapter<RecyclerView.ViewHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    private val seats = ArrayList<Seat>()
    private val clickDetail = PublishSubject.create<Seat>()

    private class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val itemView = mLayoutInflater.inflate(R.layout.list_item_empty, parent, false)
                EmptyViewHolder(itemView)
            }
            else -> {
                val itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false)
                val viewHolder = SeatViewHolder(itemView)

                RxView.clicks(itemView)
                    .takeUntil(RxView.detaches(parent))
                    .map { seats[viewHolder.adapterPosition] }
                    .subscribe(clickDetail)

                viewHolder
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val type = seats[position].id.toInt()
        if (type != 0) {
            val seat = seats[position]
            val holder = viewHolder as SeatViewHolder

            if (seat.occupied) {
                holder.ivSeatAvailable.setImageResource(R.drawable.seat_booked)
            } else {
                holder.ivSeatAvailable.setImageResource(when {
                    isSelected(position) -> R.drawable.seat_selected
                    else -> R.drawable.seat_available
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return seats.size
    }

    override fun getItemViewType(position: Int): Int {
        return seats[position].id.toInt()
    }

    fun setItems(seats: ArrayList<Seat>) {
        this.seats.clear()
        if (seats.size > 0) {
            this.seats.addAll(seats)
        }
    }

    fun updateSelection(selectedSeat: Seat): ArrayList<Long> {
        val pos = (0 until seats.size).lastOrNull { selectedSeat.id == seats[it].id } ?: 0
        toggleSelection(pos, selectedSeat.id)
        notifyDataSetChanged()
        return selectedSeats
    }

    fun getClickDetailObservable(): Observable<Seat> {
        return clickDetail
    }


    private class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivSeatAvailable = itemView.findViewById<View>(R.id.ivSeatAvailable) as ImageView
    }

}