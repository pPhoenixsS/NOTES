package com.example.notes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.dp

// Определение светлой цветовой палитры для приложения
private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF7B61FF), // Основной цвет интерфейса, используется для ключевых элементов
    secondary = Color(0xFF80DEEA), // Второстепенный цвет, акцентный
    background = Color(0xFFE8EAF6), // Цвет фона для всего приложения
    surface = Color(0xFFB39DDB), // Цвет поверхности, например, карточек и панелей
    onPrimary = Color.Black, // Цвет текста и иконок на фоне primary
    onSecondary = Color.Black, // Цвет текста и иконок на фоне secondary
    onBackground = Color.Black, // Цвет текста на общем фоне
    onSurface = Color.Black // Цвет текста на поверхностях
)

// Определение параметров типографики для приложения
private val appTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Serif, // Основной шрифт
        fontWeight = FontWeight.Bold, // Жирный стиль для крупных заголовков
        fontSize = 30.sp // Размер шрифта для крупных заголовков
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Serif, // Основной шрифт
        fontWeight = FontWeight.Medium, // Средняя насыщенность для подзаголовков
        fontSize = 24.sp // Размер шрифта для подзаголовков
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Serif, // Основной шрифт
        fontWeight = FontWeight.Normal, // Нормальная насыщенность для небольших текстов
        fontSize = 20.sp // Размер шрифта для небольших текстов
    ),
)

// Определение форм для компонентов интерфейса
private val Shapes = androidx.compose.material3.Shapes(
    small = RoundedCornerShape(4.dp), // Форма для мелких элементов, например, кнопок
    medium = RoundedCornerShape(8.dp), // Форма для средних элементов, например, карточек
    large = RoundedCornerShape(16.dp) // Форма для крупных элементов
)

// Тема приложения, объединяющая цвета, типографику и формы
@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorPalette, // Цветовая палитра
        typography = appTypography, // Настройка текстов
        shapes = Shapes, // Настройка форм компонентов
        content = content // Отображаемый контент приложения
    )
}