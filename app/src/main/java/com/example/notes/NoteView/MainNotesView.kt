package com.example.notes.NoteView

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.notes.ViewModel.NoteManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNotesView(viewModel: NoteManagerViewModel) {
    // Список заметок, загружаемый из ViewModel
    val noteList = viewModel.notes

    // Текущая редактируемая заметка, если таковая установлена
    val currentNote = viewModel.editableNote

    // Флаг, указывающий, создается ли новая заметка
    val isCreatingNote = viewModel.isCreatingNote

    // Основная структура пользовательского интерфейса
    Scaffold(
        // Верхняя панель с заголовком и опциональной кнопкой "Назад"
        topBar = {
            TopAppBar(
                // Заголовок, изменяющийся в зависимости от текущего состояния
                title = {
                    Text(
                        when {
                            currentNote == null -> "Все заметки" // Отображение списка всех заметок
                            isCreatingNote -> "Новая заметка"   // Создание новой заметки
                            else -> "Редактирование"           // Редактирование существующей заметки
                        }
                    )
                },
                // Кнопка "Назад", отображаемая только при редактировании или создании заметки
                navigationIcon = {
                    if (currentNote != null) {
                        IconButton(onClick = { viewModel.saveNote() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Вернуться" // Сбрасывает текущее редактирование
                            )
                        }
                    }
                }
            )
        },
        // Плавающая кнопка действия для добавления новой заметки или сохранения изменений
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (currentNote == null) {
                    viewModel.addNote() // Переход в режим создания новой заметки
                } else {
                    viewModel.saveNote() // Сохранение текущей редактируемой заметки
                }
            }) {
                Icon(
                    imageVector = if (currentNote == null) Icons.Default.Add else Icons.Default.Done,
                    contentDescription = if (currentNote == null) "Добавить" else "Сохранить"
                )
            }
        },
        // Основное содержимое экрана
        content = { padding ->
            if (currentNote != null) {
                // Экран редактирования заметки
                NoteEditorScreen(
                    note = currentNote, // Текущая редактируемая заметка
                    onTitleUpdated = { viewModel.updateEditableNoteTitle(it) }, // Обновление заголовка
                    onContentUpdated = { viewModel.updateEditableNoteText(it) }, // Обновление текста
                    modifier = Modifier.padding(padding) // Применение отступов
                )
            } else {
                // Сетка для отображения всех заметок
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Указание количества колонок
                    modifier = Modifier.padding(padding) // Применение отступов
                ) {
                    // Карточки заметок, каждая из которых имеет действия для редактирования и удаления
                    items(noteList) { note ->
                        NoteCard(
                            note = note, // Данные заметки
                            onEdit = { viewModel.editNote(note) }, // Переход в режим редактирования
                            onRemove = { viewModel.deleteNote(note) } // Удаление заметки
                        )
                    }
                }
            }
        }
    )
}