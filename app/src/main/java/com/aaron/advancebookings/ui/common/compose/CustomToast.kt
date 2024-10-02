package com.aaron.advancebookings.ui.common.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.utilities.constants.AppConstants

@Composable
fun CustomToast(
    messageType: MessageType,
    message: String,
    modifier: Modifier = Modifier
        .padding(
            top = 80.dp,
            start = CustomTheme.spaces.dp10,
            end = CustomTheme.spaces.dp10,
        ),
) {
    val toastIcon =
        if (messageType is MessageType.MessageSuccess) {
            painterResource(id = R.drawable.progress_tick)
        } else {
            painterResource(id = R.drawable.icon_error_exclamation)
        }

    val toastBackground =
        if (messageType is MessageType.MessageSuccess) {
            CustomTheme.colors.text2
        } else {
            CustomTheme.colors.statusError1
        }

    Box(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier,
            colors =
            CardDefaults.cardColors(
                containerColor = toastBackground
                ,
            ),
            elevation =
            CardDefaults.cardElevation(
                defaultElevation = 12.dp,
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    painter = toastIcon,
                    contentDescription = AppConstants.EMPTY_STRING,
                    modifier =
                    Modifier
                        .padding(
                            start = CustomTheme.spaces.dp16,
                            top = CustomTheme.spaces.dp16,
                            bottom = CustomTheme.spaces.dp16,
                            end = CustomTheme.spaces.dp10,
                        ).size(CustomTheme.spaces.dp16),
                )
                Text(
                    text = message,
                    style = CustomTheme.typography.bodyLink,
                    color = CustomTheme.colors.text0,
                    modifier =
                    Modifier.padding(
                        end = CustomTheme.spaces.dp10,
                    ),
                )
            }
        }
    }
}

sealed class MessageType {
    data object MessageSuccess : MessageType()

    data object MessageError : MessageType()
}

@Preview
@Composable
fun PreviewToastSuccess() {
    CustomTheme {
        CustomToast(messageType = MessageType.MessageSuccess, "Booked successfully added")
    }
}

@Preview
@Composable
fun PreviewToastError() {
    CustomTheme {
        CustomToast(messageType = MessageType.MessageError, "Booked successfully added")
    }
}
