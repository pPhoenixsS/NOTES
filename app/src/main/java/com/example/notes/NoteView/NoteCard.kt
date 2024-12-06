package com.example.notes.NoteView

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notes.entity.Note

// Функция-компонент для отображения карточки заметки с возможностью редактирования и удаления
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(note: Note, onEdit: () -> Unit, onRemove: () -> Unit) {
    // Состояние для отображения диалога подтверждения удаления
    var isDeleteDialogVisible by remember { mutableStateOf(false) }

    // Настройки модификатора карточки
    val cardStyle = Modifier
        .padding(10.dp) // Внешние отступы карточки
        .combinedClickable(
            onClick = onEdit, // Открытие для редактирования
            onLongClick = { isDeleteDialogVisible = true } // Долгое нажатие для вызова диалога удаления
        )
        .fillMaxWidth() // Занятие всей доступной ширины
        .padding(2.dp) // Внутренние отступы

    // Основной элемент карточки
    Card(
        modifier = cardStyle
    ) {
        Column(modifier = Modifier.padding(8.dp)) { // Вертикальная компоновка с внутренними отступами
            Text(
                text = note.title, // Заголовок заметки
                maxLines = 1, // Ограничение в одну строку
                overflow = TextOverflow.Ellipsis // Усечение текста при переполнении
            )
            Spacer(modifier = Modifier.height(4.dp)) // Разделитель между заголовком и текстом
            Text(
                text = note.text, // Основной текст заметки
                maxLines = 1, // Ограничение в одну строку
                overflow = TextOverflow.Ellipsis // Усечение текста при переполнении
            )
        }
    }

    // Диалог подтверждения удаления
    if (isDeleteDialogVisible) {
        AlertDialog(
            onDismissRequest = { isDeleteDialogVisible = false }, // Закрытие диалога по нажатию вне области
            title = { Text("Удалить заметку") }, // Заголовок диалога
            text = { Text("Вы действительно хотите удалить эту заметку?") }, // Текст сообщения
            confirmButton = {
                Button(
                    onClick = {
                        onRemove() // Выполнение действия удаления
                        isDeleteDialogVisible = false // Закрытие диалога после подтверждения
                    }
                ) {
                    Text("Удалить") // Текст кнопки подтверждения
                }
            },
            dismissButton = {
                Button(
                    onClick = { isDeleteDialogVisible = false } // Закрытие диалога без выполнения действия
                ) {
                    Text("Отмена") // Текст кнопки отмены
                }
            }
        )
    }
}