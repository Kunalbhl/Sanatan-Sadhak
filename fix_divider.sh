#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/fun HorizontalDivider/fun Divider/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/\<HorizontalDivider(/Divider(/g' {} +
