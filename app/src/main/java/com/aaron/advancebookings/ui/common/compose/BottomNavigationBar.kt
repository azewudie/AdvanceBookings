package com.aaron.advancebookings.ui.common.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aaron.advancebookings.ui.common.screenstate.BottomBarScreens
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.utilities.constants.AppConstants

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    bottomNavigationItem: List<BottomBarScreens>
) {
    CustomTheme {
        NavigationBar(
            containerColor = CustomTheme.colors.logoColor,
        ) {
            val currentRoute = currentRoute(navController = navController)
            bottomNavigationItem.forEach { screen ->
                val isSelected = currentRoute?.let { screen.route.contains(it) }
                if (isSelected != null) {
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Transparent,
                            selectedTextColor = Color.Transparent,
                            indicatorColor = Color.Transparent,
                            unselectedIconColor = Color.Transparent,
                            disabledIconColor = Color.Transparent,
                            disabledTextColor = Color.Transparent,
                            unselectedTextColor = Color.Transparent,
                        ),
                        selected = isSelected,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(currentRoute) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        icon = {
                            when (isSelected) {
                                true -> {
                                    when (screen.sectionName) {
                                        "Home" -> {
                                            Icon(
                                                imageVector = Icons.Default.Home,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                        "Provider" -> {
                                            Icon(
                                                imageVector = Icons.Default.AccountBox,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                        "Client" -> {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                        "Info" -> {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                    }
                                }

                                else -> {
                                    when (screen.sectionName) {
                                        "Home" -> {
                                            Icon(
                                                imageVector = Icons.Default.Home,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                        "Provider" -> {
                                            Icon(
                                                imageVector = Icons.Default.AccountBox,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                        "Client" -> {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                        "Info" -> {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = AppConstants.EMPTY_STRING,
                                                tint = CustomTheme.colors.textWhite,
                                            )
                                        }
                                    }
                                }
                            }

                        },
                        label = {
                            ResponsiveText(
                                text = screen.sectionName,
                                color = when (isSelected) {
                                    true -> CustomTheme.colors.text0
                                    else -> CustomTheme.colors.textWhite
                                },
                                textStyle = CustomTheme.typography.titleBold
                            )
                        },

                    )
                }
            }

        }
    }

}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}