package com.lunabee.lbcore.model

import android.text.TextWatcher

/**
 *
 * Child interface of [TextWatcher] to be able to distinct system watchers from our. See LBRecycler class LBEditTextWatcher
 *
 * @see TextWatcher
 *
 */
interface LBTextWatcher : TextWatcher
