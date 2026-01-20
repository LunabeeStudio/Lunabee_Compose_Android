/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.lunabee.extension

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
