package com.aaron.advancebookings.ui.common.compose

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.theme.CustomTheme

@Composable
fun CommonText(
    textAttributes: TextAttributes = TextAttributes()
) {
    CustomTheme {
        Text(
            text = textAttributes.text,
            color = textAttributes.textColor,
            style = textAttributes.textStyle,
            modifier = textAttributes.modifier,
            textAlign = textAttributes.textAlign,
            textDecoration = textAttributes.textDecoration,
            maxLines = textAttributes.maxLines,
            overflow = textAttributes.overflow

        )
    }
}

@Composable
@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
private fun PreviewCommonText() {
    CustomTheme {
        CommonText(
        )
    }
}