## 2024-05-27 - Gson Stream Parsing Optimization
**Learning:** Loading large raw JSON files (like the 900KB `exercises.json`) by buffering them completely into an enormous `String` before passing them to Gson causes unnecessary memory allocations and GC overhead in Android apps.
**Action:** Always prefer Gson's streaming API (passing a `Reader` or `InputStreamReader` directly to `Gson.fromJson()`) over loading the entire payload into memory as a `String`.
## 2026-05-30 - Room Database Schema Migration
**Learning:** When adding indices to Room database entities for performance optimization, failing to bump the database version and provide a corresponding `Migration` strategy will cause a hard crash (`IllegalStateException`) for existing users due to schema hash mismatch. Furthermore, one should never manually edit the historical `1.json` schema files.
**Action:** Always increment the database version (e.g. in `Constants.kt`), define a proper `Migration` object (e.g., executing `CREATE INDEX IF NOT EXISTS` scripts), and register it via `.addMigrations()` in the Room database builder whenever adding indices or modifying entities.
