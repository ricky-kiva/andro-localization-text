package com.dicoding.picodiploma.productdetail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}

// Localization is a way to localize app's content & resources towards some country target market
// Some resources that could be localized:
// - Texts: to set language variation (strings.xml)
// - Layout: to set layout for different size of device (layout.xml)
// - Formatting: set currency, numbering format, date format, etc
// - Accessibility: set of design, implementation, test technique to allow everyone could use the app, including people that has disability

// to add text localization, it could be done by making new `values resource folder`
// - france: values-fr
// - spain: values-es
// - etc