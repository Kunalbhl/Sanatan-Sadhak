#!/bin/bash
echo "=== Empty / Junk Files ==="
find app/src/main/java -type f -size -100c

echo "=== ViewModels ==="
find app/src/main/java -name "*ViewModel.kt"

echo "=== Repositories ==="
find app/src/main/java -name "*Repository.kt"

echo "=== Services ==="
find app/src/main/java -name "*Service.kt"

echo "=== Models ==="
find app/src/main/java -name "*Model*.kt"

echo "=== TODO / FIX ME ==="
grep -Rni "TODO" app/src/main/java
grep -Rni "FIX" app/src/main/java
