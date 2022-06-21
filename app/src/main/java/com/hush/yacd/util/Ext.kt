package com.hush.yacd.util

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val GSON = Gson()

internal inline fun <reified T> Gson.fromJson(json: String?) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

fun RecyclerView.LLM(@RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL) {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
}

fun RecyclerView.GLM(spanCount: Int = 4) {
    layoutManager = GridLayoutManager(context, spanCount)
}

fun View.setVisible(visible: Boolean = true) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setGone(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.VISIBLE
}

val EditText.textStr
    get() = text.toString()