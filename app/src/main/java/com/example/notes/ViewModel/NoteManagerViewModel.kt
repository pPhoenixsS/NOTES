package com.example.notes.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.dao.NotesRepository
import com.example.notes.entity.Note
import kotlinx.coroutines.launch

// ViewModel для управления состоянием и логикой работы с заметками
class NoteManagerViewModel(private val noteDao: NotesRepository) : ViewModel() {

    // Список заметок, обновляемый при изменении данных
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    // Редактируемая заметка, состояние которой обновляется при взаимодействии пользователя
    private var _editableNote: Note? by mutableStateOf(null)
    val editableNote: Note? get() = _editableNote

    // Флаг, указывающий, создается ли новая заметка
    private var _isCreatingNote by mutableStateOf(false)
    val isCreatingNote: Boolean get() = _isCreatingNote

    init {
        // Инициализация данных: загрузка всех заметок из базы данных
        viewModelScope.launch {
            try {
                _notes.addAll(noteDao.loadAllNotes())
            } catch (e: Exception) {
                // Добавьте обработку ошибок при загрузке данных
                Log.e("NotesViewModel", "Ошибка при загрузке заметок", e)
            }
        }
    }

    // Создание новой заметки, устанавливая её как редактируемую
    fun addNote() {
        _isCreatingNote = true
        _editableNote = Note(title = "", text = "")
    }

    // Установка существующей заметки для редактирования
    fun editNote(note: Note) {
        _isCreatingNote = false
        _editableNote = note
    }

    // Обновление заголовка редактируемой заметки
    fun updateEditableNoteTitle(title: String) {
        _editableNote = _editableNote?.copy(title = title)
    }

    // Обновление текста редактируемой заметки
    fun updateEditableNoteText(text: String) {
        _editableNote = _editableNote?.copy(text = text)
    }

    // Удаление заметки из базы данных и локального списка
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                // Удаление заметки из базы данных
                noteDao.removeNote(note)
                // Удаление заметки из локального состояния
                _notes.remove(note)
            } catch (e: Exception) {
                // Обработка ошибок при удалении заметки
                Log.e("NotesViewModel", "Ошибка при удалении заметки", e)
            }
        }
    }

    // Сохранение редактируемой заметки (новой или обновлённой) в базе данных
    fun saveNote() {
        _editableNote?.let { note ->
            viewModelScope.launch {
                try {
                    if (_isCreatingNote) {
                        // Добавление новой заметки
                        noteDao.saveNote(note)
                        _notes.add(note)
                    } else {
                        // Обновление существующей заметки
                        noteDao.editNote(note)
                        val index = _notes.indexOfFirst { it.id == note.id }
                        if (index != -1) {
                            _notes[index] = note
                        }
                    }
                    // Сброс состояния после сохранения
                    _isCreatingNote = false
                    _editableNote = null
                } catch (e: Exception) {
                    // Обработка ошибок при сохранении заметки
                    Log.e("NotesViewModel", "Ошибка при сохранении заметки", e)
                }
            }
        }
    }
}