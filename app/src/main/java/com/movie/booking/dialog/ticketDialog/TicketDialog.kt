package com.movie.booking.dialog.ticketDialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import android.graphics.drawable.ColorDrawable
import com.movie.booking.R
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.movie.booking.api.ApiUrls
import com.movie.booking.appUtils.Utils
import com.movie.booking.model.BookedMovie
import com.movie.booking.widget.QRCodeHelper
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.detail_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.json.JSONArray
import timber.log.Timber

/**
 * Created by Larence on 10/29/2020.
 */

class TicketDialog(private var appCompatActivity: AppCompatActivity) {

    private var dialog: Dialog = Dialog(appCompatActivity)
    private var ivBackdrop: ImageView? = null
    private var ivPoster: ImageView? = null
    private var ivQRCode: ImageView? = null
    private var tvMovieName: TextView? = null
    private var tvSeats: TextView? = null
    private var tvDate: TextView? = null
    private var tvTime: TextView? = null
    private var tvTicket: TextView? = null
    private var tvPrice: TextView? = null

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.ticket_dialog)
        ivBackdrop = dialog.findViewById(R.id.ivBackdrop)
        ivPoster = dialog.findViewById(R.id.ivPoster)
        ivQRCode = dialog.findViewById(R.id.ivQRCode)
        tvMovieName = dialog.findViewById(R.id.tvMovieName)
        tvSeats = dialog.findViewById(R.id.tvSeats)
        tvDate = dialog.findViewById(R.id.tvDate)
        tvTime = dialog.findViewById(R.id.tvTime)
        tvTicket = dialog.findViewById(R.id.tvTicket)
        tvPrice = dialog.findViewById(R.id.tvPrice)
    }

    fun showDialog(): Observable<Boolean> {
        return Observable.create {
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.TOP
            lp.flags = lp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
            dialog.window!!.attributes = lp

            try {
                dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }

    fun dialogDismissListener(listener: DialogInterface.OnDismissListener) {
        dialog.setOnDismissListener(listener)
    }

    fun setContent(bookedMovie: BookedMovie) {
        tvMovieName!!.text = bookedMovie.movieName
        tvSeats!!.text = bookedMovie.seat.replace("[", "").replace("]", "")
        tvDate!!.text = Utils.simpleDate(bookedMovie.date)
        tvTime!!.text = bookedMovie.time
        tvPrice!!.text = "Rs. ${bookedMovie.price}"
        tvTicket!!.text = bookedMovie.ticketId

        Picasso.get()
            .load(ApiUrls.IMAGE_PATH + bookedMovie.posterPath)
            .transform(RoundedCornersTransformation(120, 0))
            .placeholder(R.drawable.img_gallery)
            .error(R.drawable.img_gallery)
            .into(ivPoster, object : Callback {
                override fun onSuccess() {}

                override fun onError(exception: Exception?) {
                    Timber.e("onError : ${exception!!.localizedMessage}")
                }
            })

        ivQRCode!!.setImageBitmap(generateQR(bookedMovie.ticketId))
    }

    // TODO :: Need to check QR Code
    private fun generateQR(bookedMovie: String): Bitmap {
        return QRCodeHelper
            .newInstance(appCompatActivity)
            .setContent(bookedMovie)
            .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
            .setMargin(0)
            .qrcOde
    }

}