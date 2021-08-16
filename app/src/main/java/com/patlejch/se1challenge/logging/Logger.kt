package com.patlejch.se1challenge.logging

import android.util.Log

interface Logger {
    fun log(message: String)
}

class AndroidLogger: Logger {
    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }
}
