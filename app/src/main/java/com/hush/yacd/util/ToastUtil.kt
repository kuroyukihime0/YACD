package com.hush.yacd.util

import com.blankj.utilcode.util.ToastUtils

fun String.toast(){
    shortToast()
}

fun String.shortToast(){
    ToastUtils.showShort(this)
}

fun String.longToast(){
    ToastUtils.showLong(this)
}