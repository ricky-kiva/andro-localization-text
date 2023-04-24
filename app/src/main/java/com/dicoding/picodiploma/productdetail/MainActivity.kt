package com.dicoding.picodiploma.productdetail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.picodiploma.productdetail.databinding.ActivityMainBinding
import com.dicoding.picodiploma.productdetail.Helper.*
import com.dicoding.picodiploma.productdetail.Helper.Companion.withCurrencyFormat
import com.dicoding.picodiploma.productdetail.Helper.Companion.withDateFormat
import com.dicoding.picodiploma.productdetail.Helper.Companion.withNumberingFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupData()
    }

    private fun setupData() {
        val data = RemoteDataSource(this)
        val product = data.getDetailProduct()
        product.apply {
            binding.apply {
                previewImageView.setImageResource(image)
                nameTextView.text = store
                storeTextView.text = store
                colorTextView.text = color
                sizeTextView.text = size
                descTextView.text = desc
                priceTextView.text = price.withCurrencyFormat()
                dateTextView.text = getString(R.string.dateFormat, date.withDateFormat())
                ratingTextView.text = getString(R.string.ratingFormat, rating.withNumberingFormat(), countRating.withNumberingFormat())
            }
        }
        setupAccessibility(product)
    }

    private fun setupAccessibility(productModel: ProductModel) {
        productModel.apply {
            binding.apply {
                settingImageView.contentDescription = getString(R.string.settingDescription)
                previewImageView.contentDescription = getString(R.string.previewDescription)
                colorTextView.contentDescription = getString(R.string.colorDescription, color)
                sizeTextView.contentDescription = getString(R.string.sizeDescription, size)
                ratingTextView.contentDescription = getString(
                    R.string.ratingDescription,
                    rating.withNumberingFormat(),
                    countRating.withNumberingFormat()
                )
                storeTextView.contentDescription = getString(R.string.storeDescription, store)
            }
        }
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

    private fun setupAction() {
        binding.settingImageView.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
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

// Alternative Layout is a way to set specific layout for certain device size

// `smallest width` value for each devices:
// - 320dp: for smartphone (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc)
// - 480dp: for big smartphone around 5" (480x800 mdpi)
// - 600dp: for tablet around 7" (600x1024 mdpi)
// - 720dp: for big tablet around 10" (720x1280 mdpi, 800x1280 mdpi, etc)
// --- infographic: https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:c75db90253765d9cfc7c6e877379a66a20220119143508.jpeg

// usage of `smallest width`:
// - res/layout/main_activity.xml -> for device with width below 600dp
// - res/layout-sw600dp/main_activity.xml -> for device width above 600dp (7")

// `available width` could also be used instead of `smallest width`
// - res/layout/main_activity.xml -> for device with `below 600dp` available width
// - res/layout-w600dp/main_activity.xml -> for device with `600dp` available width

// `available height` could also be used:
// - res/layout-h600dp/main_activity.xml -> for device with `600dp` available height

// `orientation qualification` for landscape/portrait landscape:
// - res/layout/main_activity.xml -> for portrait
// - res/layout-land/main_activity.xml -> for landscape
// - res/layout-sw600dp/main_activity.xml -> for portrait
// - res/layout-sw600dp-land/main_activity.xml -> for landscape

// `Layout Validation` Android Studio feature to test every screen layout you could

// Information formatting in an app includes date, time, numbering, currency, etc from user's locale

// DateFormat is an abstract class to configure date & time using locale
// example usage:
// - DateFormat.getDateInstance().format(myDate)
// - DateFormat.getDateInstance(DateFormat.FULL, Locale.CANADA)

// DateFormat styles:
// - FULL -> Tuesday, April 12, 2021 AD / 3:50:12pm PST
// - LONG -> January 12, 1952 / 3:14:24pm
// - MEDIUM -> Jan 12, 1952
// - SHORT -> 12.13.52 or 3:30pm (display time / numeric number)

// SimpleDateFormat is a subclass from DateFormat that could format/parse date-string using pattern
// - example usage: SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss", Locale.ENGLISH).format(myDate)
// - example formatting:
// --- dd MMMM yyyy -> 31 Januari 2021
// --- dd-MM-yyyy -> 31-01-2021
// --- dd/MM/yy -> 31/01/21
// --- EEEE, dd MMMM yyyy -> Jumat, 01 Januari 2021

// NumberFormat is a class to format numbers & numbering
// - example usage: NumberFormat.getNumberInstance(Locale.FRENCH).format(myNumber)

// DecimalFormat is a subclass from NumberFormat to format decimal value
// - DecimalFormat supports variety of numbering:
// --- integer (212), fraction (3.14), scientific notation (1.23E4), percentage (99%), or currency ($414)
// - example usage: DecimalFormat("#,###.##")

// NumberFormat.getCurrencyInstance to do currency formatting. Usage:
/*
// NumberFormat.getCurrencyInstance()
// Locale.getDefault().country
// if (deviceLocale.equals("ID")) { mPrice *= mInExchangeRate }
 */

// `text-to-speech` is one of the `Accessibility` that could be accessed using Android's `TalkBack` app
// 2 things need to be understand to apply element's label & description, that is needed for `TalkBack`:
// - if stated on static & unchanged content, do on layout XML
// - if stated on dynamic content, do on Kotlin's file

// `android:contentDescription` is an attributes to label ImageView / ImageButton
// `android:hint` is an attributes to label EditText
// `android:labelFor` is an attributes to label any View on Android 4.2 above (API level 17)

// Tips for Accessibility:
// - add content description to any non-textual component (ImageView/ImageButton)
// - add content description to non-textual button (button with only symbol)
// - Commonly it's unnecessary to put content description to TextView / CheckBox
// - Use verbs, "Delete item" rather than "Trash Bin"
// - No use to include element name such "Button"
// - Make content description unique & accurate