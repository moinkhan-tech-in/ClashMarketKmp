package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.clash.market.models.League
import kotlin.math.absoluteValue

@Composable
fun LeagueGallery(
    leagues: List<League>,
    modifier: Modifier = Modifier,
    onLeagueSelected: (League) -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { leagues.size })

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val containerWidth = constraints.maxWidth.toFloat()
        val itemWidthPx = containerWidth * 0.6f
        val itemWidth = with(LocalDensity.current) { itemWidthPx.toDp() }

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 16.dp
        ) { page ->
            val currentOffset = (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    ).absoluteValue

            val scale = lerp(0.8f, 1f, 1f - currentOffset.coerceIn(0f, 1f))
            val alpha = lerp(0.5f, 1f, 1f - currentOffset.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .width(itemWidth)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .clickable { onLeagueSelected(leagues[page]) }
            ) {
                LeagueCard(leagues[page])
            }
        }
    }
}

@Composable
fun LeagueCard(league: League) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF222222))
            .padding(16.dp)
            .width(160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = league.iconUrls?.medium,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = league.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}