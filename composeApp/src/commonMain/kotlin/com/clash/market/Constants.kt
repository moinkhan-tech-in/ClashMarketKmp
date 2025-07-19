package com.clash.market

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