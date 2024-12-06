package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.notes.ui.theme.MyApplicationTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.dao.NotesRepository
import com.example.notes.NoteView.MainNotesView
import com.example.notes.ViewModel.NoteManagerViewModel
import com.example.notes.database.NotesDatabase

// Главная активность приложения, обеспечивающая отображение пользовательского интерфейса
class MainActivity : ComponentActivity() {

    // Инициализация ViewModel с использованием фабрики для передачи зависимости (DAO)
    private val notesViewModel: NoteManagerViewModel by viewModels {
        NotesViewModelFactory(NotesDatabase.getInstance(this).getNoteDao()) // Получаем DAO из базы данных
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включение режима отображения "от края до края" для более современного UI
        enableEdgeToEdge()

        // Установка содержимого активити с использованием Jetpack Compose
        setContent {
            // Применяем тему ко всем компонентам внутри setContent
            MyApplicationTheme {
                // Отображение экрана с заметками и привязкой ViewModel
                MainNotesView(viewModel = notesViewModel)
            }
        }
    }
}

// Фабрика для создания экземпляра NotesViewModel с передачей зависимости (DAO)
class NotesViewModelFactory(private val noteDao: NotesRepository) : ViewModelProvider.Factory {

    // Создание экземпляра ViewModel, проверка на совместимость класса
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Убедимся, что класс ViewModel поддерживается фабрикой
        if (modelClass.isAssignableFrom(NoteManagerViewModel::class.java)) {
            // Приведение типа необходимо для совместимости API
            @Suppress("UNCHECKED_CAST")
            return NoteManagerViewModel(noteDao) as T
        }
        // Исключение выбрасывается, если класс ViewModel не поддерживается фабрикой
        throw ViewModelCreationException("Unknown ViewModel class: ${modelClass.name}")
    }
}

// Кастомное исключение для создания ViewModel
class ViewModelCreationException(message: String) : IllegalArgumentException(message)