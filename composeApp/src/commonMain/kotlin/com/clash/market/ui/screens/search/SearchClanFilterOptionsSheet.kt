package com.clash.market.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_trophy
import com.clash.market.BackPressHandler
import com.clash.market.components.AutoColumnGrid
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashLabelItem
import com.clash.market.components.ClashPicker
import com.clash.market.components.ClashRangeSlider
import com.clash.market.components.ClashSlider
import com.clash.market.components.ClashStyleButtonType
import com.clash.market.components.clash.ClashLocationsList
import com.clash.market.models.Label
import com.clash.market.models.Location
import com.clash.market.models.WarFrequency
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


data class ClanFilterOptions(
    val warFrequency: WarFrequency = WarFrequency.entries[0],
    val minMember: Int = 2,
    val maxMember: Int = 50,
    val minClanPoint: Int = 2,
    val clanLevel: Int = 2,
    val labels: List<Label> = emptyList(),
    val location: Location? = null
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchClanFilterOptionsSheet(
    show: Boolean,
    clanLabelsState: List<Label>,
    clanFilterOptions: ClanFilterOptions,
    locations: List<Location>,
    onFilterApply: (ClanFilterOptions) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            var chosenLocation by remember { mutableStateOf<Location?>(null) }
            val pagerState = rememberPagerState(pageCount = { 2 })
            val scope = rememberCoroutineScope()

            HorizontalPager(
                state = pagerState,
                pageContent = {
                    when (it) {
                        0 -> {
                            var totalMembers by remember { mutableStateOf(clanFilterOptions.minMember.toFloat()..clanFilterOptions.maxMember.toFloat()) }
                            var minimumTrophies by remember { mutableStateOf(clanFilterOptions.minClanPoint.toFloat()) }
                            var warFrequency by remember { mutableStateOf(clanFilterOptions.warFrequency) }
                            val clanLabels = remember { mutableStateListOf<Label>().apply { clanFilterOptions.labels.forEach { add(it) } } }

                            Scaffold(
                                topBar = {
                                    SearchClanFilterOptionsSheetTopBar(
                                        onReset = {
                                            totalMembers = 2f..50f
                                            minimumTrophies = 2f
                                            warFrequency = WarFrequency.entries[0]
                                            chosenLocation = null
                                            clanLabels.clear()
                                        },
                                        onFilter = {
                                            scope.launch {
                                                sheetState.hide()
                                                onFilterApply(
                                                    ClanFilterOptions(
                                                        warFrequency = warFrequency,
                                                        minMember = totalMembers.start.toInt(),
                                                        maxMember = totalMembers.endInclusive.toInt(),
                                                        minClanPoint = minimumTrophies.toInt(),
                                                        location = chosenLocation,
                                                        labels = clanLabels
                                                    )
                                                )
                                                onDismiss()
                                            }

                                        }
                                    )
                                },
                                bottomBar = {
                                    HorizontalDivider(color = Color.Gray)
                                }
                            ) { innerPadding ->

                                Column(
                                    modifier = Modifier
                                        .padding(top = innerPadding.calculateTopPadding())
                                        .verticalScroll(rememberScrollState())
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    SearchOptionRow {
                                        Text(
                                            modifier = Modifier.padding(horizontal = 2.dp),
                                            text = "Clan Location",
                                            fontSize = 14.sp
                                        )
                                        ClashGlossyButton(
                                            modifier = Modifier.padding(horizontal = 2.dp),
                                            text = (chosenLocation?.name ?: "Any") + " >",
                                            onClick = {
                                                scope.launch {
                                                    pagerState.animateScrollToPage(1)
                                                }
                                            })
                                    }
                                    HorizontalDivider(color = Color.Gray)

                                    SearchOptionRow {
                                        Text("War\nFrequency", fontSize = 14.sp)
                                        Spacer(Modifier.size(12.dp))
                                        ClashPicker(
                                            WarFrequency.entries.toList(),
                                            selectedIndex = 0,
                                            onItemChange = { warFrequency = it }
                                        )
                                    }
                                    HorizontalDivider(color = Color.Gray)

                                    SearchOptionColumn {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("Members", fontSize = 14.sp)
                                            Text(
                                                "${totalMembers.start.toInt()} - ${totalMembers.endInclusive.toInt()}",
                                                fontSize = 14.sp
                                            )
                                        }
                                        ClashRangeSlider(
                                            valueRange = 1f..50f,
                                            value = totalMembers,
                                            onValueChange = { totalMembers = it },
                                            steps = 50 // adds ticks
                                        )
                                    }
                                    HorizontalDivider(color = Color.Gray)

                                    SingleValueSliderFilterItem(
                                        label = "Minimum Trophies",
                                        value = minimumTrophies,
                                        imageRes = Res.drawable.ic_trophy,
                                        onValueChange = { minimumTrophies = it }
                                    )
                                    HorizontalDivider(color = Color.Gray)

                                    if (clanLabelsState.isNotEmpty()) {
                                        SearchOptionColumn {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text("Clan Labels", fontSize = 14.sp)
                                                Text(
                                                    text = "Selected ${clanLabels.size}/3",
                                                    fontSize = 14.sp
                                                )
                                            }
                                            Spacer(Modifier.size(1.dp))
                                            AutoColumnGrid(
                                                items = clanLabelsState,
                                                minCellWidth = 150.dp,
                                                verticalSpacing = 12.dp,
                                                horizontalSpacing = 12.dp,
                                                itemContent = {
                                                    ClashLabelItem(
                                                        item = it,
                                                        showText = true,
                                                        isSelected = clanLabels.contains(it),
                                                        onClick = {
                                                            if (clanLabels.contains(it)) {
                                                                clanLabels.remove(it)
                                                            } else {
                                                                if (clanLabels.size == 3) return@ClashLabelItem
                                                                clanLabels.add(it)
                                                            }
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        1 -> {
                            BackPressHandler { scope.launch { pagerState.animateScrollToPage(0) } }
                            ClashLocationsList(
                                locations = locations,
                                onItemClick = {
                                    chosenLocation = it
                                    scope.launch {
                                        pagerState.animateScrollToPage(0)
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun SearchClanFilterOptionsSheetTopBar(
    onReset: () -> Unit = {},
    onFilter: () -> Unit = {}
) {
    Column {
        SearchOptionRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            ClashGlossyButton(
                text = "Reset",
                onClick = onReset,
                type = ClashStyleButtonType.Negative
            )
            Text(text = "Search Options")
            ClashGlossyButton(
                text = "Apply",
                onClick = onFilter,
                type = ClashStyleButtonType.Positive
            )
        }
        Spacer(Modifier.size(16.dp))
        HorizontalDivider(color = Color.Gray)
    }
}

@Composable
private fun SingleValueSliderFilterItem(
    label: String,
    value: Float,
    imageRes: DrawableResource,
    onValueChange: (Float) -> Unit
) {
    SearchOptionColumn {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = label, fontSize = 14.sp)
            Spacer(Modifier.size(8.dp))
            Text(
                text = value.toInt().toString(),
                fontSize = 14.sp
            )
            Spacer(Modifier.size(8.dp))
            Image(
                painter = painterResource(imageRes),
                contentDescription = null
            )
        }

        ClashSlider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 1f..5000f,
            steps = 50
        )
    }
}

@Composable
private fun SearchOptionRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
private fun SearchOptionColumn(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        content = content,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
}
