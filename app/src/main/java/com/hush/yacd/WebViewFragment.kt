package com.hush.yacd

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hush.yacd.data.Events
import com.hush.yacd.data.bean.FrontStyle
import com.hush.yacd.data.bean.configUrl
import com.hush.yacd.data.store.AppStore
import com.hush.yacd.data.store.ProfileStore
import com.hush.yacd.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WebViewFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Events.EVENT_RELOAD.observe(viewLifecycleOwner) {
            loadPage()
        }

        binding.webview.settings.apply {
            if (Build.VERSION.SDK_INT >= 26) safeBrowsingEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            textZoom = 100

            mixedContentMode = MIXED_CONTENT_COMPATIBILITY_MODE
            mediaPlaybackRequiresUserGesture = true
            blockNetworkImage = false
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
        }
        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        loadPage()
    }

    private fun loadPage() {
        binding.webview.loadUrl(
            FrontStyle.values()[AppStore.front_style].configUrl(ProfileStore.selectedProfile())
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}