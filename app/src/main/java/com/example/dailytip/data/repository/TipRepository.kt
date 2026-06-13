package com.example.dailytip.data.repository

import com.example.dailytip.data.local.TipDao
import com.example.dailytip.data.local.TipEntity
import kotlinx.coroutines.flow.Flow

class TipRepository(private val dao: TipDao) {

    val allTips: Flow<List<TipEntity>> = dao.getAllTips()

    suspend fun getAllTipsSnapshot(): List<TipEntity> = dao.getAllTipsSnapshot()

    suspend fun getTipById(id: Long): TipEntity? = dao.getTipById(id)

    suspend fun getTipCount(): Int = dao.getTipCount()

    suspend fun insertTip(text: String, source: String?): Long =
        dao.insertTip(TipEntity(text = text, source = source))

    suspend fun deleteTip(tip: TipEntity) = dao.deleteTip(tip)
}
