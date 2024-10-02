package com.aaron.advancebookings.ui.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.theme.CustomTheme

@Composable
fun CommonDotListComponent(
    content: String,
    showDot:Boolean = true
) {
    Row(
        modifier = Modifier.padding(
            start = CustomTheme.spaces.dp16,
            end = CustomTheme.spaces.dp16,
            bottom = CustomTheme.spaces.dp16
        ),
        horizontalArrangement = Arrangement.spacedBy(CustomTheme.spaces.dp8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showDot){
            Box(
                modifier = Modifier
                    .size(CustomTheme.spaces.dp4)
                    .background(
                        CustomTheme.colors.text0
                    )
            )
        }
        CommonText(
            TextAttributes(
                text = content,
                textColor = CustomTheme.colors.text0,
                textStyle = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        )

    }
}