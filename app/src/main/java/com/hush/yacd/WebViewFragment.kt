package com.hush.yacd

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
import androidx.fragment.app.Fragment
import com.hush.yacd.data.Config
import com.hush.yacd.data.Events
import com.hush.yacd.data.FrontStyle
import com.hush.yacd.data.configUrl
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
     
        loadPage()
    }
    
    private fun loadPage(){
        binding.webview.loadUrl(
            FrontStyle.values()[Config.front_style].configUrl(
                "192.168.50.1",
                "9990",
                "clash"
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}