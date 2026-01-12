package com.lunabee.lblogger

import co.touchlab.kermit.Logger
import org.junit.jupiter.api.Test

/**
 * Only test no crash on log extension call
 */
class LoggerExtTest {
    val logger: Logger = LBLogger.get("testLogger")

    @Test
    fun verbose_extensions_test() {
        logger.v(message = "%d", arg = 1)
        logger.v(message = "%d %s", 1, "hello")
        logger.v(Exception("Error text"), message = "%d %s", 1, "hello")
        logger.v(Exception("Error text"))
    }

    @Test
    fun debug_extensions_test() {
        logger.d(message = "%d", arg = 1)
        logger.d(message = "%d %s", 1, "hello")
        logger.d(Exception("Error text"), message = "%d %s", 1, "hello")
        logger.d(Exception("Error text"))
    }

    @Test
    fun info_extensions_test() {
        logger.i(message = "%d", arg = 1)
        logger.i(message = "%d %s", 1, "hello")
        logger.i(Exception("Error text"), message = "%d %s", 1, "hello")
        logger.i(Exception("Error text"))
    }

    @Test
    fun warn_extensions_test() {
        logger.w(message = "%d", arg = 1)
        logger.w(message = "%d %s", 1, "hello")
        logger.w(Exception("Error text"), message = "%d %s", 1, "hello")
        logger.w(Exception("Error text"))
    }

    @Test
    fun err_extensions_test() {
        logger.e(message = "%d", arg = 1)
        logger.e(message = "%d %s", 1, "hello")
        logger.e(Exception("Error text"), message = "%d %s", 1, "hello")
        logger.e(Exception("Error text"))
    }
}
