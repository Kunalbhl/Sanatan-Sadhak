#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.ArrowBack/import androidx.compose.material.icons.automirrored.filled.ArrowBack/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.ArrowForward/import androidx.compose.material.icons.automirrored.filled.ArrowForward/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.KeyboardArrowLeft/import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.KeyboardArrowRight/import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.MenuBook/import androidx.compose.material.icons.automirrored.filled.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.Article/import androidx.compose.material.icons.automirrored.filled.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.VolumeUp/import androidx.compose.material.icons.automirrored.filled.VolumeUp/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.ArrowBack/Icons.AutoMirrored.Filled.ArrowBack/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.ArrowBack/Icons.AutoMirrored.Filled.ArrowBack/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.ArrowForward/Icons.AutoMirrored.Filled.ArrowForward/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.ArrowForward/Icons.AutoMirrored.Filled.ArrowForward/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.KeyboardArrowLeft/Icons.AutoMirrored.Filled.KeyboardArrowLeft/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.KeyboardArrowLeft/Icons.AutoMirrored.Filled.KeyboardArrowLeft/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.KeyboardArrowRight/Icons.AutoMirrored.Filled.KeyboardArrowRight/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.KeyboardArrowRight/Icons.AutoMirrored.Filled.KeyboardArrowRight/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.MenuBook/Icons.AutoMirrored.Filled.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.MenuBook/Icons.AutoMirrored.Filled.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.Article/Icons.AutoMirrored.Filled.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.Article/Icons.AutoMirrored.Filled.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Default\.VolumeUp/Icons.AutoMirrored.Filled.VolumeUp/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons\.Filled\.VolumeUp/Icons.AutoMirrored.Filled.VolumeUp/g' {} +
