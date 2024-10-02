package com.aaron.advancebookings.ui.common.compose

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.theme.CustomTheme

@Composable
fun CommonButton(
    buttonTitle: String,
    color: Color = CustomTheme.colors.logoColor,
    onClick: () -> Unit
) {
    CustomTheme {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClick.invoke() },
            enabled = true,
            shape = RoundedCornerShape(CustomTheme.spaces.dp8),
            border = BorderStroke(
                width = CustomTheme.spaces.dp2,
                color = CustomTheme.colors.logoColor,
            ),
            contentPadding = PaddingValues(CustomTheme.spaces.dp0),
            interactionSource = remember {
                MutableInteractionSource()
            },
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            CommonText(
                TextAttributes(
                    text = buttonTitle,
                    textAlign = TextAlign.Center,
                    textColor = CustomTheme.colors.textWhite,
                    textStyle = CustomTheme.typography.title0Regular,
                    modifier = Modifier.padding(
                      CustomTheme.spaces.dp16
                    )
                )
            )

        }
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
private fun PreviewCommonButton() {
   CustomTheme{
       CommonButton(
           buttonTitle = stringResource(id = R.string.button_client_text)
       ) {}
   }
}