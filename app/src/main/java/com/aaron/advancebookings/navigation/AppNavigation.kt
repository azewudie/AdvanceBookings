package com.aaron.advancebookings.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.common.compose.BottomNavigationBar
import com.aaron.advancebookings.ui.common.compose.CommonAppDialog
import com.aaron.advancebookings.ui.common.screenstate.BottomBarScreens
import com.aaron.advancebookings.ui.screens.apphome.AppHomeScreen
import com.aaron.advancebookings.ui.screens.apphome.AppHomeScreenViewModel
import com.aaron.advancebookings.ui.screens.clientscreeen.ClientScreen
import com.aaron.advancebookings.ui.screens.clientscreeen.ClientScreenViewModel
import com.aaron.advancebookings.ui.screens.infoscreen.InfoScreen
import com.aaron.advancebookings.ui.screens.infoscreen.InfoScreenViewModel
import com.aaron.advancebookings.ui.screens.providerscreen.ProviderScreen
import com.aaron.advancebookings.ui.screens.providerscreen.ProviderScreenViewModel
import com.aaron.advancebookings.ui.screens.splashscreen.SplashScreen
import com.aaron.advancebookings.ui.screens.splashscreen.SplashScreenViewModel
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.utilities.constants.AppConstants

typealias SheetContent = @Composable ColumnScope.() -> Unit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen: String = AppScreens.AppHomeScreen.route
    backStackEntry?.destination?.route?.let {
        currentScreen = it
    }
    val bottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val bottomSheetConstants: SheetContent? by remember {
        mutableStateOf(null)
    }
    var showTitle by rememberSaveable { mutableStateOf(false) }
    var showTopAppBar by rememberSaveable { mutableStateOf(false) }
    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    var showHelpDialog by rememberSaveable { mutableStateOf(false) }
    val navigationBackEntry by navController.currentBackStackEntryAsState()
    showTitle = when (navigationBackEntry?.destination?.route) {
        AppScreens.AppHomeScreen.route -> true
        AppScreens.Info.route -> true
        AppScreens.Provider.route -> true
        AppScreens.Client.route -> true
        else -> false

    }
    showTopAppBar = when (navigationBackEntry?.destination?.route) {
        AppScreens.AppHomeScreen.route -> true
        AppScreens.Info.route -> true
        AppScreens.Provider.route -> true
        AppScreens.Client.route -> true
        else -> false

    }
    showBottomBar = when (navigationBackEntry?.destination?.route) {
        AppScreens.AppHomeScreen.route -> true
        AppScreens.Info.route -> true
        AppScreens.Provider.route -> true
        AppScreens.Client.route -> true
        else -> false

    }
    val bottomNavigationItem = listOf(
        BottomBarScreens.Home,
        BottomBarScreens.Provider,
        BottomBarScreens.Client,
        BottomBarScreens.Info

    )
    if (showHelpDialog) {
        CommonAppDialog(
            showDialog = remember {
                mutableStateOf(showHelpDialog)
            },
            onConfirm = {
                showHelpDialog = false
            },
            onDismiss = {
                showHelpDialog = false
            },
            buttonLabel = stringResource(id = R.string.help_dialog_button_label),
            headerText = stringResource(id = R.string.help_dialog_content)

        )
    }
    val vmStoreOwner = rememberViewModelStoreOwner()
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = { bottomSheetConstants?.invoke(this) },
        sheetShape = RoundedCornerShape(
            topStart = CustomTheme.spaces.dp13,
            topEnd = CustomTheme.spaces.dp13
        ),

        ) {
        Scaffold(
            containerColor = CustomTheme.colors.cardDefault,
            topBar = {
                if (showTopAppBar) {
                    CommonAppBar(
                        currentScreen = getScreenTitle(currentScreen),
                    ) {
                        showHelpDialog = true
                    }
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(
                        navController = navController,
                        bottomNavigationItem = bottomNavigationItem

                    )
                }
            }
        ) { innerPadding ->
            CompositionLocalProvider(LocalNavGraphViewModelStoreOwner provides vmStoreOwner) {
                NavHost(
                    navController = navController,
                    startDestination = AppScreens.SplashScreen.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(AppScreens.SplashScreen.route) {
                        val viewModel = hiltViewModel<SplashScreenViewModel>()
                        val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                        SplashScreen(
                            navController = navController,
                            screeState = screenState,
                        )
                    }
                    composable(AppScreens.AppHomeScreen.route) {
                        val viewModel = hiltViewModel<AppHomeScreenViewModel>()
                        val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                        AppHomeScreen(
                            navController = navController,
                            screeState = screenState,
                            onEvent = viewModel::onAppHomeScreenEvent
                        )
                    }
                    composable(AppScreens.Info.route) {
                        val viewModel = hiltViewModel<InfoScreenViewModel>()
                        val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                        InfoScreen(
                            screeState = screenState,
                            onEvent = viewModel::onInfoScreenEvent
                        )
                    }

                    composable(AppScreens.Provider.route) {
                        val viewModel = hiltViewModel<ProviderScreenViewModel>()
                        val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                        val progressState = viewModel.progressState.collectAsStateWithLifecycle()
                        ProviderScreen(
                            progressState = progressState,
                            screeState = screenState,
                            onEvent = viewModel::onProviderScreenEvent
                        )
                    }

                    composable(AppScreens.Client.route) {
                        val viewModel = hiltViewModel<ClientScreenViewModel>()
                        val screenState = viewModel.screenState.collectAsStateWithLifecycle()
                        val progressState = viewModel.progressState.collectAsStateWithLifecycle()
                        ClientScreen(
                            progressState = progressState,
                            screeState = screenState,
                            onEvent = viewModel::onClientScreenEvent
                        )
                    }

                }

            }
        }


    }

}

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) {
        context as ViewModelStoreOwner
    }
}

private fun getScreenTitle(appRoute: String): String {
    var title = AppConstants.EMPTY_STRING
    when (appRoute) {
        AppRouteNames.APP_HOME_SCREEN -> title = ScreenTitle.TITLE_HOME
        AppRouteNames.INFO_SCREEN -> title = ScreenTitle.TITLE_INFO
        AppRouteNames.PROVIDER_SCREEN -> title = ScreenTitle.TITLE_PROVIDER
        AppRouteNames.CLIENT_SCREEN -> title = ScreenTitle.TITLE_CLIENT
        else -> {}
    }
    return title
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CommonAppBar(
    currentScreen: String,
    onHelpClick: () -> Unit
) {
    CustomTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CustomTheme.colors.cardDefault)
        ) {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = CustomTheme.spaces.dp0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomTheme.colors.logoColor,
                    titleContentColor = CustomTheme.colors.textWhite,
                    actionIconContentColor = CustomTheme.colors.textWhite,
                    navigationIconContentColor = Color.Unspecified,
                    scrolledContainerColor = Color.Unspecified,
                ),
                title = {
                    Row {
                        Text(
                            text = currentScreen,
                            style = CustomTheme.typography.title0Regular,
                            modifier = Modifier.padding(vertical = CustomTheme.spaces.dp16)
                        )
                    }
                },
                navigationIcon = {},
                actions = {
                    Text(
                        text = stringResource(id = R.string.help),
                        style = CustomTheme.typography.title0Regular,
                        modifier = Modifier
                            .padding(vertical = CustomTheme.spaces.dp16)
                            .clickable {
                                onHelpClick()
                            }
                    )
                },
                scrollBehavior = null
            )
        }
    }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        TODO("Undefined")
    }