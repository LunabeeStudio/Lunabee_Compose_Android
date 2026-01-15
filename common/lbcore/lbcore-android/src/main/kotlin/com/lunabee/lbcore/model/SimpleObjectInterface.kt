package com.lunabee.lbcore.model

interface SimpleObjectInterface {

    fun getObjectId(): String

    fun getLabel(): String

    fun getSubLabel(): String? = null

    fun getLeftIconRes(): Int? = null

    fun getContentDescription(): String? = null
}
