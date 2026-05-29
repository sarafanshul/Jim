package com.projectdelta.jim.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.SimpleFormatter

@Suppress("MemberVisibilityCanBePrivate")
object TimeUtil {

    const val DAY_TO_MINUTE = 24 * 60L
    const val DAY_TO_SECOND = DAY_TO_MINUTE * 60
    const val DAY_TO_MILLISECOND = DAY_TO_SECOND * 1000

    /**
     * Returns number of days in span of milliseconds
     * @param timeMs time in millisecond
     * @return [Int] count of days.
     */
    fun millisecondsToDays( timeMs : Long ) =
        (timeMs / DAY_TO_MILLISECOND).toInt()

    /**
     * Returns millisecond span in days
     * @param day days in int
     * @return [Long] span in milliseconds
     */
    fun dayToMilliseconds( day : Int ) =
        day * DAY_TO_MILLISECOND

    fun getCurrentDayFromEpoch() =
        millisecondsToDays(System.currentTimeMillis())

    private val formatters = java.util.concurrent.ConcurrentHashMap<Pair<String, Locale>, ThreadLocal<SimpleDateFormat>>() // cached formatters

    /**
     * Returns the day in a formatted string.
     * @param day days since epoch,
     * @param format format specifier, default = `EEEE, dd MMMM yyyy`
     * @return formatted date
     */
    fun getDayFormatted( day : Int, format: String = "EEEE, dd MMMM yyyy" ) : String {
        // Bolt ⚡: Cache SimpleDateFormat instances to prevent expensive re-allocations
        // during frequent Jetpack Compose recompositions (e.g. DayInfoTopBarComponent)
        // ThreadLocal is used because SimpleDateFormat is not thread-safe.
        val locale = Locale.getDefault()
        val key = Pair(format, locale)
        val threadLocalSdf = formatters.getOrPut(key) {
            object : ThreadLocal<SimpleDateFormat>() {
                override fun initialValue(): SimpleDateFormat {
                    return SimpleDateFormat(format, locale)
                }
            }
        }
        val sdf = threadLocalSdf.get()!!

        // Use a new Calendar instance if needed to avoid thread-safety issues with Calendar
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = day * DAY_TO_MILLISECOND
        return sdf.format(calendar.time)
    }

}
