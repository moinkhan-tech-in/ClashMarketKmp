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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_bb_Trophy
import clashmarket.composeapp.generated.resources.ic_trophy
import com.clash.market.base.ResultState
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashLabelFlowRow
import com.clash.market.components.ClashPicker
import com.clash.market.components.ClashRangeSlider
import com.clash.market.components.ClashSlider
import com.clash.market.components.ClashStyleButtonType
import com.clash.market.components.ClashToggleButton
import com.clash.market.models.Label
import com.clash.market.models.WarFrequency
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchClanFilterOptionsSheet(
    show: Boolean,
    clanLabelsState: ResultState<List<Label>>,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {

            Scaffold { innerPadding ->

                var clanICanJoin by remember { mutableStateOf(false) }
                var familyClan by remember { mutableStateOf(false) }
                var totalMembers by remember { mutableStateOf(1f..50f) }
                var minimumTrophies by remember { mutableStateOf(1f) }
                var minimumBuilderTrophies by remember { mutableStateOf(1f) }
                var warFrequency by remember { mutableStateOf(WarFrequency.entries[0]) }

                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SearchOptionRow {
                        Text("Search Options")
                        ClashGlossyButton(text = "Reset", onClick = {
                            clanICanJoin = false
                            familyClan = false
                            totalMembers = 1f..50f
                            minimumTrophies = 1f
                            minimumBuilderTrophies = 1f
                            warFrequency = WarFrequency.entries[0]
                        }, type = ClashStyleButtonType.Negative)

                    }
                    Spacer(Modifier.size(12.dp))
                    HorizontalDivider(color = Color.Gray)

                    SearchOptionRow {
                        Text("Clan Location", fontSize = 14.sp)
                        ClashGlossyButton(text = "Any", onClick = {})
                    }

                    HorizontalDivider(color = Color.Gray)

                    SearchOptionRow {
                        Text("Only clans I can join", fontSize = 14.sp)
                        ClashToggleButton(
                            checked = familyClan,
                            onChange = { familyClan = familyClan.not() }
                        )
                    }

                    HorizontalDivider(color = Color.Gray)

                    SearchOptionRow {
                        Text("Only family friendly clan", fontSize = 14.sp)
                        ClashToggleButton(
                            checked = clanICanJoin,
                            onChange = { clanICanJoin = clanICanJoin.not() }
                        )
                    }

                    HorizontalDivider(color = Color.Gray)

                    SearchOptionRow {
                        Text("War\nFrequency", fontSize = 14.sp)
                        Spacer(Modifier.size(12.dp))
                        ClashPicker(
                            WarFrequency.entries.toList(),
                            selectedIndex = 0,
                            onItemChange = {
                                warFrequency = it
                            }
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

                    SingleValueSliderFilterItem(
                        label = "Minimum Builder Trophies",
                        value = minimumBuilderTrophies,
                        imageRes = Res.drawable.ic_bb_Trophy,
                        onValueChange = { minimumBuilderTrophies = it }
                    )

                    HorizontalDivider(color = Color.Gray)

                    if (clanLabelsState is ResultState.Success) {
                        ClashLabelFlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            list = clanLabelsState.data
                        )
                        HorizontalDivider(color = Color.Gray)
                    }
                }
            }
        }
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
            steps = 8
        )
    }
}

@Composable
private fun SearchOptionRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
        content = content
    )
}
