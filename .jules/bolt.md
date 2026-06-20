## 2024-05-23 - Room Relation Queries Cause O(N) Table Scans Without Indices
**Learning:** Room's `@Relation` queries perform full table scans on the child tables if the relational foreign key columns are not explicitly indexed. This is a massive hidden performance bottleneck for nested relationship queries (e.g., `SessionWithWorkoutWithSets`), leading to O(N^2) load times as data scales.
**Action:** Always explicitly define `indices = [Index(value = ["foreignKeyId"])]` on entities containing foreign keys used in Room `@Relation` lookups.
