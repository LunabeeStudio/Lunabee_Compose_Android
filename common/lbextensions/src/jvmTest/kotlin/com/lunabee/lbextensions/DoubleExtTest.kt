package com.lunabee.lbextensions

import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleExtTest {

    @Test
    fun formatPrice() {
        val decimalEurPrice = 3.24.formatPrice(numberLocale = Locale.UK, currency = "EUR")
        assertEquals("€3.24", decimalEurPrice, "should have decimals")

        val priceEur = 3.0.formatPrice(numberLocale = Locale.UK, currency = "EUR")
        assertEquals("€3", priceEur, "shouldn't have decimals")

        val decimalPrice = 3.24.formatPrice(numberLocale = Locale.UK)
        assertEquals("£3.24", decimalPrice, "should have decimals")

        val price = 3.0.formatPrice(numberLocale = Locale.UK)
        assertEquals("£3", price, "shouldn't have decimals")
    }
}
