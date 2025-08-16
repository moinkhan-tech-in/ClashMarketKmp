// commonMain
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import kotlin.math.max

private class CenterPinnedData : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = (parentData as? Flag ?: Flag()).copy(center = true)
    data class Flag(val center: Boolean = false)
}

fun Modifier.centerPinned(): Modifier = this.then(CenterPinnedData())

enum class SideArrangement { Start, SpaceBetween, SpaceEvenly }

@Composable
fun PinnedCenterRowDynamic(
    modifier: Modifier = Modifier,
    sideArrangement: SideArrangement = SideArrangement.SpaceBetween,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, c ->
        require(measurables.isNotEmpty()) { "PinnedCenterRowDynamic needs at least one child" }

        val flags = measurables.map {
            (it.parentData as? CenterPinnedData.Flag)?.center == true
        }
        val centerIndex = flags.indexOf(true).also {
            require(it >= 0) { "Exactly one child must have Modifier.centerPinned()" }
            require(flags.count { it } == 1) { "Only one child can be centerPinned()" }
        }

        val centerMeas = measurables[centerIndex]
        val centerPl = centerMeas.measure(Constraints(0, c.maxWidth, 0, c.maxHeight))
        val cw = centerPl.width
        val ch = centerPl.height

        val leftMeas = measurables.subList(0, centerIndex)
        val rightMeas = measurables.subList(centerIndex + 1, measurables.size)

        val sideMax = max(0, (c.maxWidth - cw) / 2)
        val sideCons = Constraints(0, sideMax, 0, c.maxHeight)

        val leftPls = leftMeas.map { it.measure(sideCons) }
        val rightPls = rightMeas.map { it.measure(sideCons) }

        val leftSum = leftPls.sumOf { it.width }
        val rightSum = rightPls.sumOf { it.width }

        val leftExtra = (sideMax - leftSum).coerceAtLeast(0)
        val rightExtra = (sideMax - rightSum).coerceAtLeast(0)

        fun gaps(count: Int, extra: Int, arrangement: SideArrangement): Pair<Int, Int> {
            if (count == 0) return 0 to 0
            return when (arrangement) {
                SideArrangement.Start -> 0 to 0
                SideArrangement.SpaceBetween -> {
                    val gaps = (count - 1).coerceAtLeast(1)
                    val g = extra / gaps
                    0 to g
                }
                SideArrangement.SpaceEvenly -> {
                    val gaps = count + 1
                    val g = extra / gaps
                    g to g
                }
            }
        }

        val (leftStartPad, leftGap) = gaps(leftPls.size, leftExtra, sideArrangement)
        val (rightStartPad, rightGap) = gaps(rightPls.size, rightExtra, sideArrangement)

        val height = max(ch, max(leftPls.maxOfOrNull { it.height } ?: 0, rightPls.maxOfOrNull { it.height } ?: 0))
            .coerceIn(c.minHeight, c.maxHeight)

        layout(c.maxWidth, height) {
            // Left side: from start → center
            var xL = 0 + leftStartPad
            leftPls.forEachIndexed { i, p ->
                p.placeRelative(xL, (height - p.height) / 2)
                xL += p.width + if (sideArrangement == SideArrangement.SpaceBetween && i == leftPls.lastIndex) 0 else leftGap
            }

            // Right side: from end ← center
            // We compute total width incl. gaps to align to end side
            val rightTotal = rightPls.sumOf { it.width } +
                    when (sideArrangement) {
                        SideArrangement.Start -> 0
                        SideArrangement.SpaceBetween -> rightGap * (rightPls.size - 1).coerceAtLeast(0)
                        SideArrangement.SpaceEvenly -> rightGap * (rightPls.size + 1)
                    }
            var xR = c.maxWidth - rightTotal
            xR += rightStartPad
            rightPls.forEachIndexed { i, p ->
                p.placeRelative(xR, (height - p.height) / 2)
                xR += p.width + if (sideArrangement == SideArrangement.SpaceBetween && i == rightPls.lastIndex) 0 else rightGap
            }

            // Center pinned exactly
            val cx = (c.maxWidth - cw) / 2
            centerPl.placeRelative(cx, (height - ch) / 2)
        }
    }
}