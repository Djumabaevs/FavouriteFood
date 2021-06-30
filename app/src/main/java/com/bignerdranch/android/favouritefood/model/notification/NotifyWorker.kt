package com.bignerdranch.android.favouritefood.model.notification

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bignerdranch.android.favouritefood.R
import com.bignerdranch.android.favouritefood.utils.Constants
import com.bignerdranch.android.favouritefood.view.activities.MainActivity
import io.reactivex.rxjava3.core.Scheduler

class NotifyWorker(context: Context, workerParams: WorkerParameters) :
Worker(context, workerParams)
{
    override fun doWork(): Result {
        Log.i("Yes", "Worker works")
        return Result.success()
    }

    private fun sendNotification() {
        val notification_id = 0

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.NOTIFICATION_ID, notification_id)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val titleNotification = applicationContext.getString(R.string.notification_title)
        val subtitleNotification = applicationContext.getString(R.string.notification_subtitle)

    }

    private fun Context.vectorToBitmap(drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0 , canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

}
