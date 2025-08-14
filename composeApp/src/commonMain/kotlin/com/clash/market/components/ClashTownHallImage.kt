package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.th1
import clashmarket.composeapp.generated.resources.th10
import clashmarket.composeapp.generated.resources.th11
import clashmarket.composeapp.generated.resources.th12
import clashmarket.composeapp.generated.resources.th13
import clashmarket.composeapp.generated.resources.th14
import clashmarket.composeapp.generated.resources.th15
import clashmarket.composeapp.generated.resources.th16
import clashmarket.composeapp.generated.resources.th17
import clashmarket.composeapp.generated.resources.th2
import clashmarket.composeapp.generated.resources.th3
import clashmarket.composeapp.generated.resources.th4
import clashmarket.composeapp.generated.resources.th5
import clashmarket.composeapp.generated.resources.th6
import clashmarket.composeapp.generated.resources.th7
import clashmarket.composeapp.generated.resources.th8
import clashmarket.composeapp.generated.resources.th9
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClashTownHallImage(modifier: Modifier = Modifier, townHall: Int?) {
    Image(
        modifier = modifier,
        painter = painterResource(townHall.getTownHallImage()),
        contentDescription = null
    )
}

fun Int?.getTownHallImage(): DrawableResource {
    return when (this) {
        1 -> Res.drawable.th1
        2 -> Res.drawable.th2
        3 -> Res.drawable.th3
        4 -> Res.drawable.th4
        5 -> Res.drawable.th5
        6 -> Res.drawable.th6
        7 -> Res.drawable.th7
        8 -> Res.drawable.th8
        9 -> Res.drawable.th9
        10 -> Res.drawable.th10
        11 -> Res.drawable.th11
        12 -> Res.drawable.th12
        13 -> Res.drawable.th13
        14 -> Res.drawable.th14
        15 -> Res.drawable.th15
        16 -> Res.drawable.th16
        17 -> Res.drawable.th17
        else -> Res.drawable.th1
    }
}