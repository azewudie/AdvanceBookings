package com.aaron.advancebookings.ui.screens.infoscreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.common.compose.CommonDotListComponent
import com.aaron.advancebookings.ui.common.compose.CommonText
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.theme.CustomTheme
import kotlinx.coroutines.flow.flow

@Composable
fun InfoScreen(
    screeState: State<InfoScreenUIStates>,
    onEvent: (InfoScreenUIEvents) -> Unit
) {
    CustomTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.cardDefault),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Column(
                modifier = Modifier
                    .background(CustomTheme.colors.cardDefault)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = CustomTheme.spaces.dp16,
                        end = CustomTheme.spaces.dp16,
                        bottom = CustomTheme.spaces.dp100
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                CommonText(
                    TextAttributes(
                        text = screeState.value.infoHeader,
                        textColor = CustomTheme.colors.text0,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = CustomTheme.spaces.dp16)
                    )
                )
                Column(modifier = Modifier.padding(horizontal = CustomTheme.spaces.dp16)) {
                    screeState.value.infoDetailList.map { content ->
                        CommonDotListComponent(
                            content = content,
                            showDot = false
                        )

                    }
                }

                screeState.value.infoFooter.map { content ->
                    CommonText(
                        TextAttributes(
                            text = content,
                            textColor = CustomTheme.colors.text0,
                            textStyle = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(bottom = CustomTheme.spaces.dp10)
                        )
                    )
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
private fun PreviewInfoScreen() {
    InfoScreen(
        screeState = flow<InfoScreenUIStates> {}.collectAsState(
            initial = InfoScreenUIStates(
                infoHeader = stringResource(R.string.info_header),
                infoDetailList = stringArrayResource(R.array.info_detail_list).toList(),
                infoFooter = stringArrayResource(R.array.info_footer).toList()
            )
        ),
        onEvent = {}
    )
}
