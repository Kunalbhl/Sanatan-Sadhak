#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/fun Divider/fun HorizontalDivider/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/\<Divider(/HorizontalDivider(/g' {} +
