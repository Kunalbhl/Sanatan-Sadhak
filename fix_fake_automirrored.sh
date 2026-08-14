#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.automirrored.filled.MenuBook/import androidx.compose.material.icons.filled.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.automirrored.filled.Article/import androidx.compose.material.icons.filled.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.automirrored.filled.VolumeUp/import androidx.compose.material.icons.filled.VolumeUp/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.MenuBook/Icons.Default.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.Article/Icons.Default.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.VolumeUp/Icons.Default.VolumeUp/g' {} +
