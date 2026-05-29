## 2024-10-27 - Caching DateFormatters
**Learning:** SimpleDateFormat and Calendar instances are expensive to create, but caching them natively introduces severe thread-safety issues (especially in asynchronous/multi-threaded contexts like Compose Recompositions) and hidden locale bugs if not paired correctly.
**Action:** Always wrap cached SimpleDateFormat instances in ThreadLocal and use the format string alongside Locale as a compound cache key.
