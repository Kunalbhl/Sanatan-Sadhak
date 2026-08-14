#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/LinearProgressIndicator(progress = { \([^}]*\) },/LinearProgressIndicator(progress = \1,/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/CircularProgressIndicator(progress = { \([^}]*\) },/CircularProgressIndicator(progress = \1,/g' {} +
