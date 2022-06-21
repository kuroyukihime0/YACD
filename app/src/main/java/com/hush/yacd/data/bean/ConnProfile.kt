package com.hush.yacd.data.bean

import java.util.*

data class ConnProfile(
    var hostName: String,
    var name: String = hostName,
    var port: String,
    var secret: String,
    val uuid:String = UUID.randomUUID().toString(),
)