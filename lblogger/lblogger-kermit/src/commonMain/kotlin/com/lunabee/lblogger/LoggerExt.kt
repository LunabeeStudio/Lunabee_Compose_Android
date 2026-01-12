package com.lunabee.lblogger

import co.touchlab.kermit.Logger

/** Log an error exception. */
fun Logger.e(t: Throwable) {
    this.e("", t)
}
