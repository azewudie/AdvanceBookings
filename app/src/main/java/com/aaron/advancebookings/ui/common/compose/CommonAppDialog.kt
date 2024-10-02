package com.aaron.advancebookings.ui.common.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.utilities.constants.AppConstants

@Composable
fun CommonAppDialog(
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    headerText: String = AppConstants.EMPTY_STRING,
    buttonLabel: String = AppConstants.EMPTY_STRING
) {
    if (showDialog.value) {
        CustomTheme {
            Dialog(
                onDismissRequest = {
                    onDismiss()
                    showDialog.value = false
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = true,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = false
                ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(CustomTheme.spaces.dp8))
                        .background(color = CustomTheme.colors.cardDefault)
                        .padding(CustomTheme.spaces.dp16),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CommonText(
                            TextAttributes(
                                text = headerText,
                                textColor = CustomTheme.colors.text0,
                                textStyle = CustomTheme.typography.titleBold,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(bottom = CustomTheme.spaces.dp16),
                                textAlign = TextAlign.Center
                            )
                        )
                        CommonButton(
                            buttonTitle = buttonLabel
                        ) {
                            onConfirm()
                            showDialog.value = false
                        }

                    }

                }
            }
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
private fun PreviewCommonAppDialog() {
   CustomTheme{
       CommonAppDialog(
           showDialog = remember {
               mutableStateOf(true)
           },
           headerText = stringResource(id = R.string.help_dialog_content),
           buttonLabel = stringResource(id = R.string.confirm_button_label),
           onConfirm = {},
           onDismiss = {}
       )
   }
}