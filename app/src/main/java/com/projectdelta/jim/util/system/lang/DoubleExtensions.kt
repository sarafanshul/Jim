package com.projectdelta.jim.util.system.lang

import java.util.Locale


/**
 * Util to format numbers as per requirement and keeping [Locale] in mind
 * [ref](https://stackoverflow.com/questions/53848189/format-number-using-decimal-format-in-kotlin)
 */
fun Double.numberFormatLocale(locale: Locale = Locale.getDefault(), formatter: String = "%,.2f") =
    formatter.format(locale, this)
