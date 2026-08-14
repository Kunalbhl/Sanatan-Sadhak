#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.automirrored.filled.Send/import androidx.compose.material.icons.filled.Send/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.Send/Icons.Default.Send/g' {} +
