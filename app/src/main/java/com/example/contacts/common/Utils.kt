package com.example.contacts.common

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

class Utils {
    companion object {
        val Int.dp: Int
            get() = (this * Resources.getSystem().displayMetrics.density).toInt()

        fun getColorForPosition(position: Int): Int {
            val colors = listOf(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.CYAN,
                Color.MAGENTA
            )

            return colors[position % colors.size]
        }
    }
}