package com.example.dailytip.domain

import com.example.dailytip.data.local.TipEntity
import com.example.dailytip.data.preferences.DailyTipPreferences
import com.example.dailytip.data.repository.TipRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import kotlin.random.Random

class DailyTipManager(
    private val repository: TipRepository,
    private val preferences: DailyTipPreferences
) {

    suspend fun getTodayTip(): TipEntity? {
        val today = LocalDate.now().toString()
        val cachedDate = preferences.cachedDate.first()
        val cachedId = preferences.cachedTipId.first()

        if (cachedDate == today && cachedId != null) {
            val tip = repository.getTipById(cachedId)
            if (tip != null) return tip
        }

        val tips = repository.getAllTipsSnapshot()
        if (tips.isEmpty()) return null

        val seed = LocalDate.now().toEpochDay()
        val selected = tips[Random(seed).nextInt(tips.size)]
        preferences.saveDailyTip(today, selected.id)
        return selected
    }
}
