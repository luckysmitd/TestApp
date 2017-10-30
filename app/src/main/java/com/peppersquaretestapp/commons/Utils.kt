package com.peppersquaretestapp.commons

import java.text.SimpleDateFormat

/**
 * Created by smit on 30/10/17.
 */
class Utils {

    companion object {
        fun convertDate(fromFormat: String, toFormat: String, sourceDate: String): String? {

            return try {

                val sdfFrom = SimpleDateFormat(fromFormat)
                val sdfTo = SimpleDateFormat(toFormat)

                val date = sdfFrom.parse(sourceDate)

                sdfTo.format(date)
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }

        }
    }
}