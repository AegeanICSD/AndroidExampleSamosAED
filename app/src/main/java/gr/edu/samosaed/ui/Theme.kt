package gr.edu.samosaed.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colors = lightColorScheme(
    primary = Color(0xFF006A67),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB9ECE6),
    onPrimaryContainer = Color(0xFF00201F),
    secondaryContainer = Color(0xFFE8EDEF),
    background = Color(0xFFF7FAF9),
    surface = Color(0xFFF7FAF9)
)

@Composable
fun SamosAedTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colors, content = content)
}
