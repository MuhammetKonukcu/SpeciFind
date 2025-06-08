package com.muhammetkonukcu.specifind.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammetkonukcu.specifind.Destination
import com.muhammetkonukcu.specifind.lang.AppLang
import com.muhammetkonukcu.specifind.lang.rememberAppLocale
import com.muhammetkonukcu.specifind.theme.AppTheme
import com.muhammetkonukcu.specifind.theme.Neutral500
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import specifind.composeapp.generated.resources.Res
import specifind.composeapp.generated.resources.create_query
import specifind.composeapp.generated.resources.past_queries

val LocalAppLocalization = compositionLocalOf {
    AppLang.English
}

@Composable
@Preview
fun MainScreen() {
    val currentLanguage = rememberAppLocale()
    CompositionLocalProvider(LocalAppLocalization provides currentLanguage) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    modifier = Modifier.systemBarsPadding(),
                    topBar = { MainTopBar() }
                ) { innerPadding ->
                    MainNavigationTab(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MainTopBar() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SpeciFind",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp)
        )
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> HomeScreen()
                    Destination.HISTORY -> HistoryScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainNavigationTab(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(modifier = modifier) { contentPadding ->
        Column {
            PrimaryTabRow(
                selectedTabIndex = selectedDestination,
                modifier = Modifier.padding(contentPadding),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                divider = {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 0.5.dp,
                        color = Neutral500
                    )
                }
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        text = {
                            Text(
                                text = stringResource(GetTabName(destination)),
                                maxLines = 1,
                                color = GetTabNameColor(selectedDestination == index),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}

@Composable
private fun GetTabName(destination: Destination): StringResource {
    return when (destination) {
        Destination.HOME -> Res.string.create_query
        Destination.HISTORY -> Res.string.past_queries
    }
}

@Composable
private fun GetTabNameColor(isSelected: Boolean): Color {
    return if (isSelected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.tertiary
}