package com.muhammetkonukcu.specifind.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.muhammetkonukcu.specifind.theme.Blue500
import com.muhammetkonukcu.specifind.theme.White
import com.muhammetkonukcu.specifind.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import specifind.composeapp.generated.resources.Res
import specifind.composeapp.generated.resources.clear
import specifind.composeapp.generated.resources.file_type
import specifind.composeapp.generated.resources.keyword
import specifind.composeapp.generated.resources.keyword_hint
import specifind.composeapp.generated.resources.language
import specifind.composeapp.generated.resources.ph_caret_down_fill
import specifind.composeapp.generated.resources.ph_caret_up_fill
import specifind.composeapp.generated.resources.safe_search
import specifind.composeapp.generated.resources.search
import specifind.composeapp.generated.resources.search_category
import specifind.composeapp.generated.resources.search_type_books
import specifind.composeapp.generated.resources.search_type_images
import specifind.composeapp.generated.resources.search_type_maps
import specifind.composeapp.generated.resources.search_type_news
import specifind.composeapp.generated.resources.search_type_video
import specifind.composeapp.generated.resources.search_type_web
import specifind.composeapp.generated.resources.show_less_customization
import specifind.composeapp.generated.resources.show_more_customization
import specifind.composeapp.generated.resources.site
import specifind.composeapp.generated.resources.sites_exclude_hint
import specifind.composeapp.generated.resources.sites_hint
import specifind.composeapp.generated.resources.sites_to_exclude
import specifind.composeapp.generated.resources.unspecified

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(bottomBar = { BottomBar(modifier = Modifier) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            LabeledOutlinedTextField(
                label = stringResource(Res.string.keyword),
                value = uiState.keyword,
                onValueChange = viewModel::onKeywordChange,
                placeholder = stringResource(Res.string.keyword_hint)
            )
            Spacer(Modifier.height(12.dp))

            LabeledOutlinedTextField(
                label = stringResource(Res.string.site),
                value = uiState.sites,
                onValueChange = viewModel::onSitesChange,
                placeholder = stringResource(Res.string.sites_hint)
            )
            Spacer(Modifier.height(12.dp))

            OptionDropdownMenu(
                label = stringResource(Res.string.file_type),
                options = GetFileTypes(),
                selectedOption = uiState.fileType,
                onOptionSelected = viewModel::onFileTypeChange,
                placeholder = stringResource(Res.string.unspecified)
            )
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.safe_search),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Switch(
                    checked = uiState.safeSearchEnabled,
                    onCheckedChange = viewModel::onSafeSearchToggle,
                    colors = GetSwitchColors()
                )
            }
            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(
                    if (uiState.showMoreOptions)
                        Res.string.show_less_customization
                    else
                        Res.string.show_more_customization
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = Blue500,
                modifier = Modifier
                    .clickable { viewModel.onShowMoreOptionsToggle() }
                    .padding(vertical = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(12.dp))

            if (uiState.showMoreOptions) {
                LabeledOutlinedTextField(
                    label = stringResource(Res.string.sites_to_exclude),
                    value = uiState.excludedSites,
                    onValueChange = viewModel::onExcludedSitesChange,
                    placeholder = stringResource(Res.string.sites_exclude_hint)
                )
                Spacer(Modifier.height(12.dp))

                OptionDropdownMenu(
                    label = stringResource(Res.string.language),
                    options = GetLanguages(),
                    selectedOption = uiState.language,
                    onOptionSelected = viewModel::onLanguageChange,
                    placeholder = stringResource(Res.string.unspecified)
                )
                Spacer(Modifier.height(12.dp))

                OptionDropdownMenu(
                    label = stringResource(Res.string.search_category),
                    options = GetSearchCategories(),
                    selectedOption = uiState.searchCategory,
                    onOptionSelected = viewModel::onSearchCategoryChange,
                    placeholder = stringResource(Res.string.unspecified)
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun LabeledOutlinedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            colors = GetTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun OptionDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = selectedOption,
            onValueChange = { /* read-only */ },
            placeholder = {
                val textColor =
                    if (placeholder == stringResource(Res.string.unspecified))
                        MaterialTheme.colorScheme.tertiary
                    else MaterialTheme.colorScheme.primary
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor
                )
            },
            readOnly = true,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) vectorResource(Res.drawable.ph_caret_up_fill) else vectorResource(
                        Res.drawable.ph_caret_down_fill
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coords ->
                    textFieldSize = coords.size.toSize()
                }
                .clickable { expanded = !expanded },
            colors = GetTextFieldColors(),
            enabled = false
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        if (index == 0) onOptionSelected("") else onOptionSelected(option)
                        expanded = false
                    })
            }
        }
    }
}

@Composable
private fun BottomBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp)) {
        BottomButton(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.clear),
            colors = GetClearButtonColors(),
            onClick = {

            }
        )

        Spacer(modifier.width(16.dp))

        BottomButton(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.search),
            colors = GetSearchButtonColors(),
            onClick = {

            }
        )
    }
}

@Composable
private inline fun BottomButton(
    modifier: Modifier,
    label: String,
    colors: ButtonColors,
    crossinline onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        colors = colors,
        onClick = { onClick.invoke() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun GetClearButtonColors(): ButtonColors {
    val colors = ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.background
    )
    return colors
}

@Composable
private fun GetSearchButtonColors(): ButtonColors {
    val colors = ButtonDefaults.buttonColors().copy(
        containerColor = Blue500,
        contentColor = White
    )
    return colors
}

@Composable
private fun GetFileTypes(): List<String> {
    val fileTypes = listOf(
        stringResource(Res.string.unspecified),
        "PDF",
        "DOC",
        "DOCX",
        "PPT",
        "PPTX",
        "XLS",
        "XLSX",
        "TXT",
        "ZIP"
    )
    return fileTypes
}

@Composable
private fun GetLanguages(): List<String> {
    val fileTypes = listOf(
        stringResource(Res.string.unspecified),
        "en",
        "tr"
    )
    return fileTypes
}

@Composable
private fun GetSearchCategories(): List<String> {
    return listOf(
        stringResource(Res.string.unspecified),
        stringResource(Res.string.search_type_web),
        stringResource(Res.string.search_type_images),
        stringResource(Res.string.search_type_news),
        stringResource(Res.string.search_type_video),
        stringResource(Res.string.search_type_books),
        stringResource(Res.string.search_type_maps)
    )
}

@Composable
private fun GetTextFieldColors(): TextFieldColors {
    val colors = TextFieldDefaults.colors().copy(
        focusedTextColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        errorContainerColor = MaterialTheme.colorScheme.background,
        disabledTextColor = MaterialTheme.colorScheme.primary,
        disabledIndicatorColor = MaterialTheme.colorScheme.tertiary,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        disabledPlaceholderColor = MaterialTheme.colorScheme.tertiary,
    )
    return colors
}

@Composable
private fun GetSwitchColors(): SwitchColors {
    val colors = SwitchDefaults.colors().copy(
        checkedThumbColor = Blue500,
        uncheckedThumbColor = MaterialTheme.colorScheme.tertiary,
        checkedTrackColor = Blue500.copy(alpha = 0.3f),
        uncheckedTrackColor = MaterialTheme.colorScheme.background,
        checkedBorderColor = Blue500,
        uncheckedBorderColor = MaterialTheme.colorScheme.tertiary
    )
    return colors
}