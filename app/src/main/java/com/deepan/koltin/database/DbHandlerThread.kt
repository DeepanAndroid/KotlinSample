package com.deepan.koltin.database

import android.os.Handler
import android.os.HandlerThread

class DbHandlerThread(threadName: String, priority: Int) : HandlerThread(threadName, priority) {

    private lateinit var mHandler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mHandler = Handler(looper)
        HandlerThread.MIN_PRIORITY
    }

    fun postTask(task: Runnable) {
        mHandler.post(task)
    }

}