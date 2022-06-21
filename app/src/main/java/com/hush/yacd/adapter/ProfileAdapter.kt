package com.hush.yacd.adapter

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.chad.library.adapter.base.module.DraggableModule
import com.hush.yacd.R
import com.hush.yacd.data.bean.ConnProfile
import com.hush.yacd.data.store.ProfileStore
import com.hush.yacd.databinding.ItemConnProfileBinding

class ProfileAdapter :
    BaseBindingAdapter<ConnProfile, ItemConnProfileBinding>(ItemConnProfileBinding::inflate),
    DraggableModule {

    init {
        draggableModule.apply {
            isSwipeEnabled = true
            setOnItemSwipeListener(object : OnItemSwipeListener {
                override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

                }

                override fun clearView(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

                }

                override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

                }

                override fun onItemSwipeMoving(
                    canvas: Canvas?,
                    viewHolder: RecyclerView.ViewHolder?,
                    dX: Float,
                    dY: Float,
                    isCurrentlyActive: Boolean
                ) {
                }

            })
        }
    }

    override fun convert(holder: VBViewHolder<ItemConnProfileBinding>, item: ConnProfile) {
        with(holder.viewBinding) {
            if (item.uuid == ProfileStore.selectedProfile) {
                root.setBackgroundResource(R.drawable.shape_profile_bg_selected)
                ivEdit.setImageResource(R.mipmap.ic_checked)
            } else {
                root.setBackgroundResource(R.drawable.shape_profile_bg_unselected)
                ivEdit.setImageResource(R.mipmap.ic_edit_profile)
            }
            tvName.text = item.name
            tvContent.text = "${item.hostName}:${item.port}"
        }
    }

}