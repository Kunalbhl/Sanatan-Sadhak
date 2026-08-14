#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.ArrowBack/Icons.Default.ArrowBack/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.ArrowForward/Icons.Default.ArrowForward/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.KeyboardArrowLeft/Icons.Default.KeyboardArrowLeft/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.KeyboardArrowRight/Icons.Default.KeyboardArrowRight/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.MenuBook/Icons.Default.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.Article/Icons.Default.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.AutoMirrored\.Filled\.VolumeUp/Icons.Default.VolumeUp/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.automirrored.filled/import androidx.compose.material.icons.filled/g' {} +
