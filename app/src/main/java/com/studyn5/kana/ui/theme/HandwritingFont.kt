package com.studyn5.kana.ui.theme

import android.content.Context
import android.graphics.Typeface

/**
 * Load font viết tay KleeOne (giống nét bút Nhật) từ assets.
 */
object HandwritingFont {
    private var cached: android.graphics.Typeface? = null

    fun get(context: Context): android.graphics.Typeface {
        if (cached == null) {
            cached = try {
                Typeface.createFromAsset(context.assets, "fonts/KleeOne-SemiBold.ttf")
            } catch (e: Exception) {
                Typeface.DEFAULT
            }
        }
        return cached!!
    }
}
