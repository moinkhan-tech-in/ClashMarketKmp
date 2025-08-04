package com.clash.market

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

val TagRegEx = Regex("^#?[a-zA-Z0-9]{0,10}$")

fun withHashPrefixField(input: TextFieldValue): TextFieldValue {
    if (input.text.isEmpty()) return TextFieldValue()

    val cleaned = input.text.replace("#", "")
    val newText = "#${cleaned.uppercase()}"
    return TextFieldValue(
        text = newText.uppercase(),
        selection = TextRange(newText.length)
    )
}

fun openPlayerLink(tag: String): String {
    val encodedTag = tag.replace("#", "")
    return "https://link.clashofclans.com/en?action=OpenPlayerProfile&tag=$encodedTag"
}

fun openClanLink(tag: String): String {
    val encodedTag = tag.replace("#", "")
    return "https://link.clashofclans.com/?action=OpenClanProfile&tag=$encodedTag"
}

fun openClanWarLink(tag: String): String {
    val encodedTag = tag.replace("#", "")
    return "https://link.clashofclans.com/en?action=OpenClanWarLog&tag=$encodedTag"
}

fun openGameLink(): String {
    return "https://link.clashofclans.com/en?action=OpenGame"
}