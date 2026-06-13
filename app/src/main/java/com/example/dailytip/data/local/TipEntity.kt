package com.example.dailytip.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tips")
data class TipEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val text: String,
    val source: String?,
    val dateAdded: Long = System.currentTimeMillis()
)
