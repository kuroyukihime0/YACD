package com.hush.yacd.util

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtil {

    private const val CONFIRM = "CONFIRM"
    private const val CANCEL = "CANCEL"

    fun alert(
        context: Context,
        msg: CharSequence?,
        isCancelable: Boolean = false,
        btn: String? = CONFIRM,
        onClickListener: DialogInterface.OnClickListener? = null
    ) {
        MaterialAlertDialogBuilder(context).apply {
            setMessage(msg)
            setCancelable(isCancelable)
            setNegativeButton(btn, onClickListener)
            create().apply { show() }
        }
    }

    fun dialogWith2Button(
        context: Context,
        title: String? = null,
        msg: String? = null,
        btnPositive: String? = null,
        callbackPositive: (() -> Unit)? = null,
        btnNegative: String? = null,
        callbackNegative: (() -> Unit)? = null,
    ) {
        MaterialAlertDialogBuilder(context).apply {
            if (!title.isNullOrEmpty()) {
                setTitle(title)
            }
            if (!msg.isNullOrEmpty()) {
                setMessage(msg)
            }
            setNegativeButton(btnNegative) { dialog, which -> callbackNegative?.invoke() }
            setPositiveButton(btnPositive) { dialog, which -> callbackPositive?.invoke() }
            create().apply { show() }
        }
    }

    fun singleChoice(
        context: Context,
        title: String? = null,
        msg: String? = null,
        choices: Array<String>? = null,
        checked: Int = 0,
        callback: ((Int) -> Unit)? = null,
    ) {
        MaterialAlertDialogBuilder(context).apply {
            if (!title.isNullOrEmpty()) {
                setTitle(title)
            }
            if (!msg.isNullOrEmpty()) {
                setMessage(msg)
            }
            var checkedTemp = checked
            setSingleChoiceItems(choices, checked) { dialog, which -> checkedTemp = which }
            setPositiveButton(CONFIRM) { dialog, which -> callback?.invoke(checkedTemp) }
            setNegativeButton(CANCEL) { dialog, which -> }
            create().apply { show() }
        }
    }

    fun multiChoiceDialog(
        context: Context,
        title: String? = null,
        msg: String? = null,
        choices: Array<String> = arrayOf(),
        checked: IntArray = intArrayOf(),
        callback: ((BooleanArray) -> Unit)? = null,
    ) {
        MaterialAlertDialogBuilder(context).apply {
            if (!title.isNullOrEmpty()) {
                setTitle(title)
            }
            if (!msg.isNullOrEmpty()) {
                setMessage(msg)
            }
            val checkedTemp =
                choices.mapIndexed { index, s -> checked.contains(index) }.toBooleanArray()
            setMultiChoiceItems(choices, checkedTemp) { dialog, which, isChecked ->
                checkedTemp[which] = isChecked
            }
            setPositiveButton(CONFIRM) { dialog, which -> callback?.invoke(checkedTemp) }
            setNegativeButton(CANCEL) { dialog, which -> }
            create().apply {
                show()
            }
        }
    }

    fun inputDialog(
        context: Context,
        title: String? = null,
        hint: String? = null,
        cancelable: Boolean = true,
        callback: ((String) -> Unit)? = null,
    ) {
        MaterialAlertDialogBuilder(context).apply {
            if (!title.isNullOrEmpty()) {
                setTitle(title)
            }
            val et = EditText(context)
            et.setText(hint)
            setView(et)
            setPositiveButton(CONFIRM) { dialog, which -> callback?.invoke(et.text.toString()) }
            if (cancelable) {
                setCancelable(true)
                setNegativeButton(CANCEL) { dialog, which -> }
            } else {
                setCancelable(false)
            }
            create().apply { show() }
        }
    }

    fun customDialog(
        context: Context,
        cancelable: Boolean = true,
        view: View,
    ) = MaterialAlertDialogBuilder(context).apply {
            setView(view)
            setCancelable(cancelable)

        }.create().apply { show() }

}