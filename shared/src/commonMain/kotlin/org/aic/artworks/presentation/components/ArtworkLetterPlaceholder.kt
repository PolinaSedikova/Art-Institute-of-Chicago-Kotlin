package org.aic.artworks.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun ArtworkLetterPlaceholder(
    title: String,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    fontSize: Int = 22
) {
    val initialLetter = title.firstOrNull { it.isLetter() }?.uppercaseChar()?.toString() ?: "?"
    val backgroundColor = generatePaletteColor(title)

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initialLetter,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

private fun generatePaletteColor(seed: String): Color {
    val palette = listOf(
        Color(0xFF8B2635),
        Color(0xFF2E5266),
        Color(0xFF43766C),
        Color(0xFF6E5773),
        Color(0xFF8E4A49),
        Color(0xFF355C7D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B),
        Color(0xFF4A5568),
        Color(0xFF2B4C7E),
        Color(0xFF5B7065),
        Color(0xFF7A4444)
    )
    val index = abs(seed.hashCode()) % palette.size
    return palette[index]
}
