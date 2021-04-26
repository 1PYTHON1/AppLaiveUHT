package com.laivedatos.uht_v2

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.common.internal.Objects
import kotlinx.android.synthetic.main.activity_visor_excel.*

class VisorExcelActivity : AppCompatActivity() {
    // Private
    private val  BASE_URL="https://docs.google.com/spreadsheets/d/e/2PACX-1vQrdtFX5_UaqoBVPCNlajG_RIz0BjT6lMkIoA3EelIrpT6heWiYJpnCPiJidNIpgVlC7eSV2U1CzbML/pubhtml"
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visor_excel)
        title = "Resumen De Incidencias"
        //Refresh
        swipeRefresh.setOnRefreshListener {
            webViewEcxel.reload()
        }

        ///Web View
        webViewEcxel.webChromeClient = object : WebChromeClient(){

        }
        webViewEcxel.webViewClient = object  : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                swipeRefresh.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                swipeRefresh.isRefreshing = false
            }




        }
        val setting = webViewEcxel.settings
        setting.javaScriptEnabled = true
        webViewEcxel.loadUrl(BASE_URL)

    }
}