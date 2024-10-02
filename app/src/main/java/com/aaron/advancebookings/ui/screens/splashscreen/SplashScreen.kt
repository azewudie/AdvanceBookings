package com.aaron.advancebookings.ui.screens.splashscreen

import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aaron.advancebookings.R
import com.aaron.advancebookings.navigation.AppScreens
import com.aaron.advancebookings.ui.common.compose.AppLogo
import com.aaron.advancebookings.ui.common.compose.CommonText
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.utilities.constants.AppConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@Composable
fun SplashScreen(
    navController: NavController,
    screeState: State<SplashScreenUIStates>,
) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = AppConstants.ANIMATION_DURATION_TIME,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                })
        )
        delay(AppConstants.ANIMATION_DELAY_TIME)
        navController.navigate(AppScreens.AppHomeScreen.route)
    }
    CustomTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.backGrounds),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(CustomTheme.spaces.dp15)
                    .size(CustomTheme.spaces.dp330)
                    .scale(scale.value),
                shape = CircleShape,
                color = CustomTheme.colors.cardDefault,
                border = BorderStroke(
                    width = CustomTheme.spaces.dp2,
                    color = CustomTheme.colors.text0
                )
            ) {
                Column(
                    modifier = Modifier.padding(CustomTheme.spaces.dp1),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AppLogo(
                        logoText = screeState.value.appLogo
                    )
                    Spacer(
                        modifier = Modifier
                            .height(CustomTheme.spaces.dp15)
                    )
                    CommonText(
                        TextAttributes(
                            text = screeState.value.appHeaderText,
                            textStyle = CustomTheme.typography.largeTitleRegular,
                            textColor = CustomTheme.colors.text0
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
private fun PreviewSplashScreen() {
    CustomTheme {
        SplashScreen(
            navController = rememberNavController(),
            screeState = flow<SplashScreenUIStates> {}.collectAsState(
                initial = SplashScreenUIStates(
                    appLogo = stringResource(R.string.app_logo),
                    appHeaderText = stringResource(R.string.app_header_text)
                )
            ),
        )
    }
}