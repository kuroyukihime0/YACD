package com.hush.yacd.data

enum class FrontStyle(val url: String) {
    YACD("file:///android_asset/yacd/index.html?hostname=%s&port=%s&secret=%s#/proxies"),
    RAZORD("https://clash.razord.top/#/proxies?host=%s&port=%s&secret=%s"),
}

fun FrontStyle.configUrl(hostName: String, port: String, secret: String): String {
    return url.format(hostName, port, secret)
}