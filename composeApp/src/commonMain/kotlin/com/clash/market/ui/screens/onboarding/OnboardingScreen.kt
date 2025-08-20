package com.clash.market.ui.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_villeger_girl
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.widgets.ClashImage
import com.clash.market.components.widgets.ClashImageSpec
import com.clash.market.navigation.ScreenRouts

data class OnboardingItem(
    val title: String,
    val description: String,
    val imageSpec: ClashImageSpec? = null,
    val primaryCtaText: String? = null
)

val OnBoardingData = listOf(
    OnboardingItem(
        title = "Your Journey, Your Profile",
        description = "Track your progress, troops, heroes, and achievements all in one place.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl)
    ),
    OnboardingItem(
        title = "Power in Numbers",
        description = "See your clan’s strength, members, and performance to stay battle-ready.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl)
    ),
    OnboardingItem(
        title = "Discover & Connect",
        description = "Search any clan or player, explore profiles, and connect with the community.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl)
    ),
    OnboardingItem(
        title = "Climb the Leaderboards",
        description = "Check top players and clans worldwide, or explore rankings in your region.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl)
    ),
    OnboardingItem(
        title = "Witness the Battle Live",
        description = "Follow ongoing wars in real time and stay updated on every attack and defense.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl)
    ),
    OnboardingItem(
        title = "Ready to Begin?",
        description = "Jump in now and explore your Clash stats like never before.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl),
        primaryCtaText = "Get Started"
    )
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OnboardingScreen(
    onNavigate: (ScreenRouts) -> Unit
) {
    Surface {
        val pagerState = rememberPagerState(pageCount = { OnBoardingData.size })
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingItemContent(
                    getStartedClick = { onNavigate(ScreenRouts.EnterProfile) },
                    item = OnBoardingData[page]
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                val animatedProgress by animateFloatAsState(
                    targetValue = (pagerState.currentPage + 1) / (pagerState.pageCount.toFloat()),
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing,
                    ),
                    label = "pager-progress"
                )

                OnboardingProgressFromPager(pagerState)
            }
        }
    }
}

@Composable
private fun OnboardingItemContent(
    getStartedClick: () -> Unit,
    item: OnboardingItem
) {
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(
            12.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item.imageSpec?.let { ClashImage(modifier = Modifier.fillMaxWidth(), image = it) }

        Text(
            textAlign = TextAlign.Center,
            text = item.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            textAlign = TextAlign.Center,
            text = item.description,
            style = MaterialTheme.typography.bodyMedium
        )
        item.primaryCtaText?.let {
            ClashGlossyButton(
                onClick = getStartedClick,
                text = it
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OnboardingProgressFromPager(pagerState: PagerState) {
    val pages = pagerState.pageCount.coerceAtLeast(1)
    val progress = (
            (pagerState.currentPage + 1 + pagerState.currentPageOffsetFraction) /
                    pages.coerceAtLeast(1)
            ).coerceIn(0f, 1f)

    LinearWavyProgressIndicator(
        progress = { progress },              // <-- lambda reads state each frame
        modifier = Modifier.fillMaxWidth(),
        trackColor = Color(0xFFE0C97F),
        color = MaterialTheme.colorScheme.primary
    )
}