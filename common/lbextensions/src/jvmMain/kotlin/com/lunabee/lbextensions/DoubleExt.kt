package com.lunabee.lbextensions

import java.text.NumberFormat
import java.util.Currency
import java.util.HashMap
import java.util.Locale
import kotlin.collections.set

private object PriceFormatPool {
    private val numberInstances: MutableMap<String, NumberFormat> by lazy {
        HashMap(5)
    }

    fun getInstance(numberLocale: Locale, currency: String?): NumberFormat {
        val key: String = numberLocale.country + (currency ?: "")

        if (!numberInstances.containsKey(key)) {
            val formatter = NumberFormat.getCurrencyInstance(numberLocale)

            formatter.currency = if (key.isNotEmpty() && currency != null) {
                try {
                    Currency.getInstance(currency)
                } catch (e: Exception) {
                    // This case can be found if currency string is not a good one
                    Currency.getInstance(numberLocale)
                }
            } else {
                Currency.getInstance(numberLocale)
            }

            numberInstances[key] = formatter
        }

        return numberInstances[key]!!
    }
}

/**
 * Return a human readable price for the default device locale.
 *
 * @param numberLocale The locale of the price to format it specifically to the locale. For example: 12,34€ in french is
 *  EUR 12.34 in the USA.
 * @param currency The ISO 4217 code of the currency.
 * @return A formatted price strings with 2 decimal if the price a is exact one.
 */
fun Double.formatPrice(numberLocale: Locale = Locale.getDefault(), currency: String? = null): String {
    val formatter = PriceFormatPool.getInstance(numberLocale, currency)
    formatter.maximumFractionDigits = if ((this - this.toInt().toDouble()) != 0.0) 2 else 0
    formatter.minimumFractionDigits = if ((this - this.toInt().toDouble()) != 0.0) 2 else 0
    return formatter.format(this)
}
