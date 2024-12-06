package com.example.notes.dao

import androidx.room.*
import androidx.room.Dao
import com.example.notes.entity.Note

// Интерфейс для управления данными заметок в базе данных
@Dao
interface NotesRepository {
    // Извлечение всех записей из таблицы "notes"
    @Query("SELECT * FROM notes")
    suspend fun loadAllNotes(): List<Note> // Асинхронно возвращает список всех записей

    // Добавление новой записи в базу данных
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: Note) // Сохраняет заметку, заменяя дубликаты

    // Изменение существующей записи
    @Update
    suspend fun editNote(note: Note) // Обновляет данные существующей заметки

    // Удаление записи из базы данных
    @Delete
    suspend fun removeNote(note: Note) // Удаляет выбранную заметку
}