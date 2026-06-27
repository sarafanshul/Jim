## 2024-05-27 - Gson Stream Parsing Optimization
**Learning:** Loading large raw JSON files (like the 900KB `exercises.json`) by buffering them completely into an enormous `String` before passing them to Gson causes unnecessary memory allocations and GC overhead in Android apps.
**Action:** Always prefer Gson's streaming API (passing a `Reader` or `InputStreamReader` directly to `Gson.fromJson()`) over loading the entire payload into memory as a `String`.

## 2024-05-24 - Database indices on relations
**Learning:** Room relations like `@Relation` perform implicit secondary queries on child tables filtering by the matching keys. If the child table lacks indices on these foreign keys (e.g. `workoutId`, `exerciseId`), Room will perform a full table scan for every parent record. This degrades performance severely as database size grows.
**Action:** Always add `@Entity(indices = [Index(value = ["foreign_key_column"])])` to child entities in Room when they are frequently loaded via `@Relation`, and provide a migration strategy so existing users don't face app crashes.
