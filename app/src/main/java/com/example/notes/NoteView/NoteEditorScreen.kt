package com.example.notes.NoteView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notes.entity.Note

// Экран изменения заметки с полями для заголовка и содержимого
@Composable
fun NoteEditorScreen(
    note: Note, // Заметка, которую редактируем
    onTitleUpdated: (String) -> Unit, // Обработка обновления заголовка
    onContentUpdated: (String) -> Unit, // Обработка обновления содержимого
    modifier: Modifier = Modifier // Настройка внешнего вида и расположения элементов
) {
    // Вертикальная компоновка элементов
    Column(
        modifier = modifier
            .fillMaxSize() // Использует всё доступное пространство
            .padding(16.dp) // Внутренние отступы
    ) {
        // Поле ввода для изменения заголовка
        TextField(
            value = note.title, // Текущее значение заголовка
            onValueChange = onTitleUpdated, // Обработчик изменения текста
            label = { Text("Название заметки") }, // Метка поля ввода
            modifier = Modifier
                .fillMaxWidth() // Полная ширина поля
                .height(100.dp), // Ограничение высоты
        )
        // Промежуток между элементами
        Spacer(modifier = Modifier.height(16.dp))
        // Поле ввода для изменения содержимого заметки
        TextField(
            value = note.text, // Текущее значение текста заметки
            onValueChange = onContentUpdated, // Обработчик изменения текста
            label = { Text("Содержимое заметки") }, // Метка поля ввода
            modifier = Modifier
                .fillMaxWidth() // Полная ширина поля
                .height(200.dp), // Ограничение высоты
        )
    }
}