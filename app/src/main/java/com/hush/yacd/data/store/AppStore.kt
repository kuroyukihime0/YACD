package com.hush.yacd.data.store

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvInt


object AppStore : MMKVOwner {

    var front_style by mmkvInt(default = 0)

}
