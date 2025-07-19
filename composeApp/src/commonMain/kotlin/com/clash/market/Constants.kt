package com.clash.market

val TagRegEx = Regex("^#?[a-zA-Z0-9]{0,10}$")

fun getOpenPlayerLink(tag: String): String {
    val encodedTag = tag.replace("#", "")
    return "https://link.clashofclans.com/en?action=OpenPlayerProfile&tag=$encodedTag"
}

fun getOpenClanLink(tag: String): String {
    val encodedTag = tag.replace("#", "")
    return "https://link.clashofclans.com/?action=OpenClanProfile&tag=$encodedTag"
}


fun getOpenClanWarLink(tag: String): String {
    val encodedTag = tag.replace("#", "")
    return "https://link.clashofclans.com/en?action=OpenClanWarLog&tag=$encodedTag"
}