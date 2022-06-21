package com.hush.yacd

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hush.yacd.adapter.ProfileAdapter
import com.hush.yacd.data.Events
import com.hush.yacd.data.bean.ConnProfile
import com.hush.yacd.data.store.ProfileStore
import com.hush.yacd.databinding.FragmentSecondBinding
import com.hush.yacd.databinding.LayoutEditProfileBinding
import com.hush.yacd.util.LLM
import com.hush.yacd.util.textStr
import com.hush.yacd.util.toast


@SuppressLint("NotifyDataSetChanged")
class UserProfileFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val connProfileAdapter by lazy {
        ProfileAdapter().apply {
            setNewInstance(ProfileStore.getProfiles().toMutableList())
            addChildClickViewIds(R.id.iv_edit)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.iv_edit -> {
                        val item = data[position]
                        showEditDialog(item, position)
                    }
                }
            }
            setOnItemClickListener { adapter, view, position ->
                val item = data[position]
                ProfileStore.selectProfile(item.uuid)
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvProfileList.LLM(RecyclerView.VERTICAL)
            rvProfileList.adapter = connProfileAdapter

            fab.setOnClickListener {
                showEditDialog()
            }
        }
    }

    private fun showEditDialog(old: ConnProfile? = null, pos: Int = 0) {
        val title = if (old == null) "Add Profile" else "Edit Profile"
        val dialogView = LayoutEditProfileBinding.inflate(layoutInflater)
        if (old != null) {
            dialogView.inputEtAddress.setText(old.hostName)
            dialogView.inputEtPort.setText(old.port)
            dialogView.inputEtName.setText(old.name)
            dialogView.inputEtSecret.setText(old.secret)
        }
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(title)
            setView(dialogView.root)
            setPositiveButton("Save") { _, _ ->

            }
            setNegativeButton("Cancel") { _, _ ->

            }
            create().apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val inputName = dialogView.inputEtName.textStr
                        val inputSecret = dialogView.inputEtSecret.textStr
                        val inputPort = dialogView.inputEtPort.textStr
                        val inputHost = dialogView.inputEtAddress.textStr
                        if (inputHost.isBlank()) {
                            val errMsg = "Host is required"
                            errMsg.toast()
                            dialogView.inputEtAddress.error = errMsg
                            return@setOnClickListener
                        }
                        if (inputPort.isBlank()) {
                            val errMsg = "Port is required"
                            errMsg.toast()
                            dialogView.inputEtPort.error = errMsg
                            return@setOnClickListener
                        }
                        if (old == null) {
                            val new = ConnProfile(
                                hostName = inputHost,
                                port = inputPort,
                                secret = inputSecret,
                                name = inputName.ifBlank { dialogView.inputEtAddress.textStr },
                            )
                            ProfileStore.addProfile(new)
                            connProfileAdapter.addData(new)
                            connProfileAdapter.notifyItemInserted(connProfileAdapter.itemCount - 1)
                        } else {
                            val olds = ProfileStore.getProfiles()
                            olds.find { it.uuid == old.uuid }?.apply {
                                hostName = inputHost
                                port = inputPort
                                secret = inputSecret
                                name = inputName.ifBlank { dialogView.inputEtAddress.textStr }
                            }
                            ProfileStore.saveProfiles(olds)

                            old.apply {
                                hostName = inputHost
                                port = inputPort
                                secret = inputSecret
                                name = inputName.ifBlank { dialogView.inputEtAddress.textStr }
                            }
                            connProfileAdapter.notifyItemChanged(pos)
                        }
                        Events.EVENT_RELOAD.postData(true)
                        dismiss()
                    }
                }
                show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}