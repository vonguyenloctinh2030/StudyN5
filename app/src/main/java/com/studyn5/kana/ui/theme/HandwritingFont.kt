package com.studyn5.kana.ui.theme

import android.content.Context
import android.graphics.Typeface
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.studyn5.kana.R

val KanaFontFamily = FontFamily(Font(R.font.kleeone))

/**
 * Load font viết tay KleeOne dùng chung cho Canvas Android.
 */
object HandwritingFont {
    private var cached: android.graphics.Typeface? = null

    fun get(context: Context): android.graphics.Typeface {
        if (cached == null) {
            cached = try {
                context.resources.getFont(R.font.kleeone)
            } catch (e: Exception) {
                Typeface.DEFAULT
            }
        }
        return cached!!
    }
}
