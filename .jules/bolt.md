## 2024-05-28 - Avoid large file buffer into memory
**Learning:** For a ~900KB json file containing 18k lines, buffering into a `StringWriter` line-by-line via `BufferedReader` wastes memory and GC time. The Gson library natively handles `InputStreamReader` streams which avoids holding the entire string in memory at once.
**Action:** Use streaming parsers/readers whenever directly possible instead of loading an entire large file as a single String object into memory.
