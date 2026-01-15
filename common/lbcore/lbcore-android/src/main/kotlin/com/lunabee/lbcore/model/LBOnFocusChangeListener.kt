package com.lunabee.lbcore.model

import android.view.View

/**
 *
 * Child interface of [View.OnFocusChangeListener] to be able to distinct system focus listener from our. See LBRecycler class
 * LBTextInputEditText.
 *
 * @see View.OnFocusChangeListener
 *
 */
interface LBOnFocusChangeListener : View.OnFocusChangeListener
