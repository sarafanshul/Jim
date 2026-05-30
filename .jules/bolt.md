## 2024-05-27 - Gson Stream Parsing Optimization
**Learning:** Loading large raw JSON files (like the 900KB `exercises.json`) by buffering them completely into an enormous `String` before passing them to Gson causes unnecessary memory allocations and GC overhead in Android apps.
**Action:** Always prefer Gson's streaming API (passing a `Reader` or `InputStreamReader` directly to `Gson.fromJson()`) over loading the entire payload into memory as a `String`.

## 2024-10-27 - Caching DateFormatters
**Learning:** SimpleDateFormat and Calendar instances are expensive to create, but caching them natively introduces severe thread-safety issues (especially in asynchronous/multi-threaded contexts like Compose Recompositions) and hidden locale bugs if not paired correctly.
**Action:** Always wrap cached SimpleDateFormat instances in ThreadLocal and use the format string alongside Locale as a compound cache key.
