package com.clash.market.ui.screens.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_barbarian_clan
import clashmarket.composeapp.generated.resources.ic_barbarian_fight
import clashmarket.composeapp.generated.resources.ic_villeger_girl
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.widgets.ClashImage
import com.clash.market.components.widgets.ClashImageSpec
import com.clash.market.navigation.ScreenRouts
import kotlinx.coroutines.launch

data class OnboardingItem(
    val title: String,
    val description: String,
    val imageSpec: ClashImageSpec? = null
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
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_barbarian_clan)
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
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_barbarian_fight)
    ),
    OnboardingItem(
        title = "Ready to Begin?",
        description = "Jump in now and explore your Clash stats like never before.",
        imageSpec = ClashImageSpec.Res(Res.drawable.ic_villeger_girl)
    )
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OnboardingScreen(
    onNavigate: (ScreenRouts) -> Unit
) {
    Surface {
        val pagerState = rememberPagerState(pageCount = { OnBoardingData.size })
        Column(modifier = Modifier.displayCutoutPadding().fillMaxSize().padding(vertical = 16.dp)) {
            OnboardingProgressFromPager(pagerState)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingItemContent(item = OnBoardingData[page])
            }

            val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
            Crossfade(targetState = isLastPage) {
                val scope = rememberCoroutineScope()
                ClashGlossyButton(
                    onClick = {
                        scope.launch {
                            if (isLastPage) {
                                onNavigate(ScreenRouts.EnterProfile)
                            } else {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        }
                    },
                    text = if (isLastPage) "Get Started" else "Next",
                    modifier = Modifier.fillMaxWidth().padding(32.dp)
                )
            }
        }
    }
}


@Composable
private fun OnboardingItemContent(
    modifier: Modifier = Modifier,
    item: OnboardingItem
) {
    Column(
        modifier = modifier.padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.Bottom),
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

    FillMaxWidthPlus(extra = 16.dp) { mod ->
        LinearWavyProgressIndicator(
            progress = { progress },
            modifier = mod,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = .35f),
            color = MaterialTheme.colorScheme.primary,
            stopSize = 0.dp
        )
    }
}

@Composable
fun FillMaxWidthPlus(
    extra: Dp = 16.dp,
    content: @Composable (Modifier) -> Unit
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val w = maxWidth + extra * 2
        content(
            Modifier
                .requiredWidth(w)   // wider than parent
                .offset(x = -extra) // center: extra on both sides
        )
    }
}