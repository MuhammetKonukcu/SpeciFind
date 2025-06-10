package com.muhammetkonukcu.specifind.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.collectAsLazyPagingItems
import com.muhammetkonukcu.specifind.room.entity.HistoryEntity
import com.muhammetkonukcu.specifind.viewmodel.HistoryViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import specifind.composeapp.generated.resources.Res
import specifind.composeapp.generated.resources.delete_query
import specifind.composeapp.generated.resources.file_type
import specifind.composeapp.generated.resources.language
import specifind.composeapp.generated.resources.safe_search
import specifind.composeapp.generated.resources.search
import specifind.composeapp.generated.resources.search_category
import specifind.composeapp.generated.resources.site
import specifind.composeapp.generated.resources.sites_to_exclude

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HistoryScreen() {
    val viewModel = koinViewModel<HistoryViewModel>()
    val historyPagingItems = viewModel.historyPagingDataFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = historyPagingItems.itemCount,
            key = { index -> historyPagingItems[index]?.id ?: 0 }) { index ->
            val historyItem = historyPagingItems[index]
            historyItem?.let {
                HistoryItem(entity = it, viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun HistoryItem(
    entity: HistoryEntity,
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = entity.keyword,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        if (!entity.site.isNullOrBlank()) {
            Text(
                text = stringResource(Res.string.site) + ": ${entity.site}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (!entity.fileType.isNullOrBlank()) {
            Text(
                text = stringResource(Res.string.file_type) + ": ${entity.fileType}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (!entity.searchCategory.isNullOrBlank()) {
            Text(
                text = stringResource(Res.string.search_category) + ": ${entity.searchCategory}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (!entity.language.isNullOrBlank()) {
            Text(
                text = stringResource(Res.string.language) + ": ${entity.language}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (!entity.excludeSite.isNullOrBlank()) {
            Text(
                text = stringResource(Res.string.sites_to_exclude) + ": ${entity.excludeSite}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (entity.safeSearch) {
            Text(
                text = stringResource(Res.string.safe_search),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        val uriHandler = LocalUriHandler.current
        Row(modifier = modifier.fillMaxWidth()) {
            BottomButton(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.delete_query),
                colors = GetClearButtonColors(),
                isEnabled = true,
                onClick = { viewModel.removeQueryFromHistory(id = entity.id) }
            )

            Spacer(modifier.width(16.dp))

            BottomButton(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.search),
                colors = GetSearchButtonColors(),
                isEnabled = true,
                onClick = {
                    val query = viewModel.buildQuery(entity = entity)
                    uriHandler.openUri("https://www.google.com/search?q=$query")
                }
            )
        }
    }
}