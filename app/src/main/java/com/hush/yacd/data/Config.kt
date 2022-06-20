package com.hush.yacd.data

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvInt
import com.dylanc.mmkv.mmkvString


object Config : MMKVOwner {

    var front_style by mmkvInt(default = 0)
}
