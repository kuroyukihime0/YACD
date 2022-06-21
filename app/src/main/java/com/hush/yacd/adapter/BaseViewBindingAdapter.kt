package com.hush.yacd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BaseBindingAdapter<T, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    data: MutableList<T>? = null,
) :
    BaseQuickAdapter<T, VBViewHolder<VB>>(0, data) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VBViewHolder<VB> {
        val viewBinding = inflate.invoke(LayoutInflater.from(context), parent, false)
        return VBViewHolder(viewBinding)
    }
}

class VBViewHolder<VB : ViewBinding>(val viewBinding: VB) : BaseViewHolder(viewBinding.root)