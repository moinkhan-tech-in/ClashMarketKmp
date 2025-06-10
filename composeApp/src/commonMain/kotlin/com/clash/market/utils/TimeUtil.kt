package com.clash.market.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.until
import kotlin.time.ExperimentalTime

fun parseWarTime(raw: String): Instant {
    // Handles both formats:
    // 1. "20240614T080000Z"
    // 2. "20240614T080000.000Z"
    val sanitized = raw
        .replace(".", "") // remove milliseconds dot
        .replace("Z", "") // remove Z temporarily

    val date = sanitized.substring(0, 8)  // yyyyMMdd
    val time = sanitized.substring(9, 15) // HHmmss

    val iso8601 = buildString {
        append(date.substring(0, 4)).append("-")
        append(date.substring(4, 6)).append("-")
        append(date.substring(6, 8)).append("T")
        append(time.substring(0, 2)).append(":")
        append(time.substring(2, 4)).append(":")
        append(time.substring(4, 6)).append("Z")
    }

    return Instant.parse(iso8601)
}

@OptIn(ExperimentalTime::class)
fun getTimeRemainingLabel(targetTime: Instant): String {
    val now = Clock.System.now()
    val durationMillis = targetTime.toEpochMilliseconds() - now.toEpochMilliseconds()

    return if (durationMillis <= 0) {
        "Ended"
    } else {
        val totalSeconds = durationMillis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        "${hours}h ${minutes}m ${seconds}s"
    }
}

fun getTimeAgoLabel(targetTime: Instant): String {
    val now = Clock.System.now()
    val totalSeconds = targetTime.until(now, DateTimeUnit.SECOND)

    return when {
        totalSeconds < 60 -> "$totalSeconds seconds ago"
        totalSeconds < 3600 -> "${totalSeconds / 60} minutes ago"
        totalSeconds < 86400 -> "${totalSeconds / 3600} hours ago"
        totalSeconds < 2592000 -> "${totalSeconds / 86400} days ago"
        totalSeconds < 31536000 -> {
            val months = totalSeconds / 2592000
            val remainingDays = (totalSeconds % 2592000) / 86400
            buildString {
                append("$months month")
                if (months > 1) append("s")
                if (remainingDays > 0) {
                    append(" $remainingDays day")
                    if (remainingDays > 1) append("s")
                }
                append(" ago")
            }
        }
        else -> "${totalSeconds / 31536000} years ago"
    }
}