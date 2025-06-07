package com.clash.market.ui.screens.search.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.base.ResultState
import com.clash.market.components.ClashSearchTextField
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanListItem
import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchClanContent(
    clanSearchState: ResultState<List<ClanDetail>>,
    onQuerySubmit: (String) -> Unit = {},
    onNavigate: (ScreenRouts) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(Modifier.size(16.dp))
        var value by remember { mutableStateOf("India") }
        ClashSearchTextField(
            value = value,
            onQuerySubmit = onQuerySubmit,
            onValueChange = { value = it },
            btnText = "Search",
            hint = "Clan name",
            btnEnabled = { value.length >= 3 }
        )
        ResultStateCrossFade(
            resultState = clanSearchState,
            idealContent = {
                Text(
                    modifier = Modifier.padding(top = 120.dp),
                    text = "Chief, Start searching for a clan.",
                    fontFamily = ClashFont
                )
            }
        ) { result ->
            LazyVerticalGrid(
                columns = Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 56.dp)
            ) {
                items(result) { it ->
                    ClanListItem(
                        clanDetail = it,
                        onClick = { onNavigate(ScreenRouts.ClanDetail(it.tag.orEmpty())) })
                }
            }
        }
    }
}

@Composable
@Preview
private fun SearchClanContentPreview() {
    SearchClanContent(
        clanSearchState = ResultState.Success(
            listOf(FakeClanDetailItem, FakeClanDetailItem, FakeClanDetailItem)
        )
    )
}