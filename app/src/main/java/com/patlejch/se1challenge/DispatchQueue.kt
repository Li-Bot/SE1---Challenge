package com.patlejch.se1challenge

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object DispatchQueue {
    private val pool = Executors.newScheduledThreadPool(4)

    fun execute(runnable: Runnable) = pool.execute(runnable)
    fun shutdown() {
        pool.awaitTermination(1, TimeUnit.SECONDS)
        pool.shutdown()
    }
}
