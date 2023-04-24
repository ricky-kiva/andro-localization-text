package com.dicoding.picodiploma.productdetail

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {
        fun String.withNumberingFormat(): String {
            return NumberFormat.getNumberInstance().format(this.toDouble())
        }

        fun String.withDateFormat(): String {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = format.parse(this) as Date
            // will search for user locale automatically
            return DateFormat.getDateInstance(DateFormat.FULL).format(date)
        }

        fun String.withCurrencyFormat(): String {
            val rupiahExchangeRate = 124995.95
            val euroExchangeRate = 0.88

            var priceOnDollar = this.toDouble() / rupiahExchangeRate

            // will search for user locale automatically
            var mCurrencyFormat = NumberFormat.getCurrencyInstance()
            val deviceLocale = Locale.getDefault().country
            when {
                deviceLocale.equals("ES") -> {
                    priceOnDollar *= euroExchangeRate
                }
                deviceLocale.equals("ID") -> {
                    priceOnDollar *= rupiahExchangeRate
                }
                else -> {
                    // it sets the default locale, if user locale is not accessible
                    mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
                }
            }
            return mCurrencyFormat.format(priceOnDollar)
        }
    }
}