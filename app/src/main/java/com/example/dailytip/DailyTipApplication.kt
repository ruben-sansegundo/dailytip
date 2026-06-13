package com.example.dailytip

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dailytip.data.local.TipDatabase
import com.example.dailytip.data.preferences.DailyTipPreferences
import com.example.dailytip.data.repository.TipRepository
import com.example.dailytip.domain.DailyTipManager
import com.example.dailytip.widget.WidgetRefreshWorker
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class DailyTipApplication : Application() {

    val database by lazy { TipDatabase.getInstance(this) }
    val repository by lazy { TipRepository(database.tipDao()) }
    val preferences by lazy { DailyTipPreferences(this) }
    val dailyTipManager by lazy { DailyTipManager(repository, preferences) }

    override fun onCreate() {
        super.onCreate()
        scheduleDailyWidgetRefresh()
    }

    private fun scheduleDailyWidgetRefresh() {
        val now = LocalDateTime.now()
        val nextMidnight = LocalDate.now().plusDays(1).atStartOfDay()
        val initialDelay = Duration.between(now, nextMidnight).toMillis()

        val request = PeriodicWorkRequestBuilder<WidgetRefreshWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyWidgetRefresh",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
