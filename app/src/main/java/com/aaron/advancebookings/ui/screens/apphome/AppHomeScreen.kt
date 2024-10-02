package com.aaron.advancebookings.ui.screens.apphome

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.ui.common.compose.CommonButton
import com.aaron.advancebookings.ui.common.compose.CommonDotListComponent
import com.aaron.advancebookings.ui.common.compose.CommonText
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import kotlinx.coroutines.flow.flow
@Composable
fun AppHomeScreen(
    navController: NavController,
    screeState: State<AppHomeScreenUIStates>,
    onEvent: (AppHomeScreenUIEvents) -> Unit
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
                        text = screeState.value.appHomeHeader,
                        textColor = CustomTheme.colors.text0,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = CustomTheme.spaces.dp16)
                    )
                )
                Column(modifier = Modifier.padding(horizontal = CustomTheme.spaces.dp16)) {
                    screeState.value.appHomeDetailList.map { content ->
                        CommonDotListComponent(content = content)
                    }
                }
                CommonText(
                    TextAttributes(
                        text = screeState.value.appHomeFooter,
                        textColor = CustomTheme.colors.text0,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = CustomTheme.spaces.dp16)
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CustomTheme.spaces.dp16),
                ) {
                    CommonButton(
                        buttonTitle = screeState.value.appHomeClientButton
                    ) {
                        onEvent.invoke(AppHomeScreenUIEvents.OnClickGetStartClient(navController))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CustomTheme.spaces.dp16),
                ) {
                    CommonButton(
                        buttonTitle = screeState.value.appHomeProviderButton
                    ) {
                        onEvent.invoke(AppHomeScreenUIEvents.OnClickGetStartProvider(navController))
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
private fun PreviewAppHomeScreen() {
    CustomTheme {
        AppHomeScreen(
            navController = rememberNavController(),
            screeState = flow<AppHomeScreenUIStates> {}.collectAsState(
                initial = AppHomeScreenUIStates(
                    appHomeHeader = stringResource(R.string.app_home_header),
                    appHomeDetailList = stringArrayResource(R.array.app_home_detail_list).toList(),
                    appHomeFooter = stringResource(R.string.app_home_footer),
                    appHomeClientButton = stringResource(R.string.button_client_text),
                    appHomeProviderButton = stringResource(R.string.button_provider_text),
                )
            ),
            onEvent = {}
        )
    }
}