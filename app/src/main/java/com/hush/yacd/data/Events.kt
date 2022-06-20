package com.hush.yacd.data

import com.fmt.livedatabus.LiveDataBus

object Events {

    val EVENT_RELOAD = LiveDataBus.with<Boolean>("RELOAD")  //通知开关VPN

}