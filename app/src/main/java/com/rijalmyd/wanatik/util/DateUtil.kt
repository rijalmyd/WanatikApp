package com.rijalmyd.wanatik.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDateTimeFormatted(): String {
    return try {
        val currentDateTime = Date()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id", "ID"))
        dateFormat.format(currentDateTime)
    } catch (e: Exception) {
        ""
    }
}
