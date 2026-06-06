## 2024-05-27 - Gson Stream Parsing Optimization
**Learning:** Loading large raw JSON files (like the 900KB `exercises.json`) by buffering them completely into an enormous `String` before passing them to Gson causes unnecessary memory allocations and GC overhead in Android apps.
**Action:** Always prefer Gson's streaming API (passing a `Reader` or `InputStreamReader` directly to `Gson.fromJson()`) over loading the entire payload into memory as a `String`.
## 2024-06-25 - Room Relations and Full Table Scans
**Learning:** In Room database, using `@Relation` annotations without providing explicit indices for the mapped parent/child columns leads to significant performance sinks because Room performs full-table scans to resolve the relationships.
**Action:** Always verify that foreign keys or columns heavily used in `@Relation` queries are indexed using `@Entity(indices = [...])`, increment the Database version, and implement proper Migration scripts to deploy indices safely to existing users.
