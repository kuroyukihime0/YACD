package com.hush.yacd.data.bean

enum class FrontStyle(val url: String) {
    YACD("file:///android_asset/yacd/index.html?hostname=%s&port=%s&secret=%s#/configs"),
    RAZORD("http://clash.razord.top/#/proxies?host=%s&port=%s&secret=%s"),
}

fun FrontStyle.configUrl(connConfig: ConnProfile): String {
    return url.format(connConfig.hostName, connConfig.port, connConfig.secret)
}