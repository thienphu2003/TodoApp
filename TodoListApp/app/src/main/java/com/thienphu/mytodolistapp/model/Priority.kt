package com.thienphu.mytodolistapp.model

import androidx.compose.ui.graphics.Color
import com.thienphu.mytodolistapp.ui.theme.highPriorityColor
import com.thienphu.mytodolistapp.ui.theme.lowPriorityColor
import com.thienphu.mytodolistapp.ui.theme.mediumPriorityColor
import com.thienphu.mytodolistapp.ui.theme.nonePriorityColor

enum class Priority (val color : Color) {
    HIGH(highPriorityColor),
    MEDIUM(mediumPriorityColor),
    LOW(lowPriorityColor),
    NONE(nonePriorityColor)
}