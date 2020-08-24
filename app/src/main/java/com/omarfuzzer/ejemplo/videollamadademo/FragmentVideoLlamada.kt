package com.omarfuzzer.ejemplo.videollamadademo

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_video_llamada.*


class FragmentVideoLlamada(var asesor: String) : Fragment() {
//    private val urlWeb: String = "file:///android_asset/events.html"
//    private val urlWeb: String = "file:///android_asset/eventos.html"
    private val urlWeb: String = "https://demofuzzer.herokuapp.com/eventos.html"
//    private val urlWeb: String = "https://appr.tc/r/777739241"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility", "ObsoleteSdkInt")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WebView.setWebContentsDebuggingEnabled(true)
        WebView.setWebContentsDebuggingEnabled(true)
        wv_videollamada.settings.javaScriptEnabled = true
        wv_videollamada.clearCache(true)
        wv_videollamada.webViewClient = WebViewClient()
        wv_videollamada.settings.mediaPlaybackRequiresUserGesture = false
        wv_videollamada.settings.domStorageEnabled = true
        wv_videollamada.settings.allowUniversalAccessFromFileURLs = true
        wv_videollamada.settings.allowFileAccessFromFileURLs = true
        wv_videollamada.settings.setAppCacheEnabled(true)
        wv_videollamada.settings.loadsImagesAutomatically = true
        wv_videollamada.settings.allowFileAccessFromFileURLs = true



        CookieManager.getInstance().setAcceptThirdPartyCookies(wv_videollamada, true)


//        wv_videollamada.webChromeClient = object : WebChromeClient() {
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            override fun onPermissionRequest(request: PermissionRequest) {
//                request.grant(request.resources)
//            }
//        }


        wv_videollamada.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {

                activity!!.runOnUiThread(Runnable {
                    if (request.origin.toString() == "file:///") {
                        request.grant(request.resources)
                    } else {
                        request.grant(request.resources)
                    }


                })
            }
        }
        wv_videollamada.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }

        wv_videollamada.addJavascriptInterface(WebAppInterface(context!!, this), "Android")

        wv_videollamada.loadUrl(urlWeb)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_llamada, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(asesor: String) = FragmentVideoLlamada(asesor)
        val TAG = FragmentVideoLlamada::class.java.canonicalName
    }

    private fun llamadaEstablecida() {
        //TODO hacer algo cuando la llamada esta establecida
    }

    private fun llamadaFinalizada() {
        //TODO hacer algo al finalizar la videollamada
    }

    private fun llamar() {
        Toast.makeText(context, "listo para llamar", Toast.LENGTH_LONG).show()

        wv_videollamada.post(Runnable {
            wv_videollamada.evaluateJavascript(
                "javascript: llamar(\"${asesor}\");",
                null
            )
        })
    }

    class WebAppInterface(
        private val context: Context,
        private val fragment: FragmentVideoLlamada
    ) {
        @JavascriptInterface
        fun llamadaEstablecida() {
            Toast.makeText(context, "Llamada establecida", Toast.LENGTH_LONG).show()
            fragment.llamadaEstablecida()
        }

        @JavascriptInterface
        fun llamadaFinalizada() {
            Toast.makeText(context, "Llamada Finalizada", Toast.LENGTH_LONG).show()
            fragment.llamadaFinalizada()
        }

        @JavascriptInterface
        fun registrado() {
            Toast.makeText(context, "Registrado", Toast.LENGTH_LONG).show()
            fragment.llamar()
        }
    }
}