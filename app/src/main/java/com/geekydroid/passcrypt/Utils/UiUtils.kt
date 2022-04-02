package com.geekydroid.passcrypt.Utils

import android.content.res.Resources

object UiUtils {



    fun getDisplayHeight() = Resources.getSystem().displayMetrics.heightPixels

    fun getDisplayWidth() = Resources.getSystem().displayMetrics.widthPixels

}