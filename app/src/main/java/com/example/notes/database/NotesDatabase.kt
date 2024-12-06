package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.dao.NotesRepository
import com.example.notes.entity.Note

// База данных для приложения заметок, реализованная с помощью библиотеки Room
@Database(entities = [Note::class], version = 1) // Указание сущностей и версии базы
abstract class NotesDatabase : RoomDatabase() {
    // Абстрактный метод для получения экземпляра DAO
    abstract fun getNoteDao(): NotesRepository

    companion object {
        // Потокобезопасное хранение экземпляра базы данных
        @Volatile
        private var databaseInstance: NotesDatabase? = null

        // Метод для создания или получения текущего экземпляра базы данных
        fun getInstance(context: Context): NotesDatabase {
            // Синхронизированная проверка существующего экземпляра
            return databaseInstance ?: synchronized(this) {
                // Инициализация базы данных через билдер
                val newInstance = Room.databaseBuilder(
                    context.applicationContext, // Контекст приложения
                    NotesDatabase::class.java, // Ссылка на класс базы данных
                    "notes" // Название файла базы данных
                ).build()
                databaseInstance = newInstance // Сохранение экземпляра
                newInstance // Возврат созданного экземпляра
            }
        }
    }
}