package com.movie.booking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.movie.booking.R
import com.movie.booking.adapter.seatBooking.SelectableAdapter
import com.movie.booking.model.Date
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlin.collections.ArrayList

/**
 * Created by Larence on 10/29/2020.
 */

class DateAdapter(private val appCompatActivity: AppCompatActivity) :
    SelectableAdapter<DateAdapter.DateViewHolder>() {

    // Initialize data
    private val arrayList: ArrayList<Date> = ArrayList()
    private val clickDetail = PublishSubject.create<Date>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_calendar_item, parent, false)
        val viewHolder = DateViewHolder(view)

        RxView.clicks(view)
            .takeUntil(RxView.detaches(parent))
            .map { arrayList[viewHolder.adapterPosition] }
            .subscribe(clickDetail)

        return viewHolder
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date: Date = arrayList[position]

        holder.tvDay.text = date.dayOfWeek
        holder.tvDate.text = date.day
        holder.tvMonth.text = date.month

        when {
            isSelected(position) -> {
                holder.tvDate.setBackgroundResource(R.drawable.circle_bg_selected)
                holder.tvDate.setTextColor(appCompatActivity.resources.getColor(R.color.white))
            }
            else -> {
                holder.tvDate.setBackgroundResource(R.drawable.circle_bg)
                holder.tvDate.setTextColor(appCompatActivity.resources.getColor(R.color.textColor))
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    /**
     * Replace the data of the array list
     * @param date a model that holds data of date from current to next 10 days
     */
    fun setDate(date: ArrayList<Date>) {
        this.arrayList.clear()
        if (date.isNotEmpty())
            this.arrayList.addAll(date)
        notifyDataSetChanged()
    }

    fun updateSelection(selectedDate: Date) {
        clearSelection()
        val pos =
            (0 until arrayList.size)
                .lastOrNull {
                    selectedDate.date.equals(arrayList[it].date, true)
                } ?: 0
        toggleSelection(pos, 0L)
        notifyDataSetChanged()
    }

    fun getClickDetailObservable(): Observable<Date> {
        return clickDetail
    }


    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay = itemView.findViewById<View>(R.id.tvDay) as TextView
        var tvDate = itemView.findViewById<View>(R.id.tvDate) as TextView
        var tvMonth = itemView.findViewById<View>(R.id.tvMonth) as TextView
    }

}