package com.example.notes.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Модель данных для заметки, представляющая собой сущность в базе данных
@Entity(tableName = "notes") // Указание имени таблицы в базе данных
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Уникальный идентификатор заметки, автоматически генерируется
    val title: String, // Заголовок заметки
    val text: String // Текст содержимого заметки
)
