package com.bignerdranch.android.favouritefood.model.notification

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.reactivex.rxjava3.core.Scheduler

class NotifyWorker(context: Context, workerParams: WorkerParameters) :
Worker(context, workerParams)
{
    override fun doWork(): Result {
        Log.i("Yes", "Worker works")
        return Result.success()
    }

}
