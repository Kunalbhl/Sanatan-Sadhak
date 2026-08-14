#!/bin/bash
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.ArrowBack/Icons.AutoMirrored.Filled.ArrowBack/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.ArrowBack/Icons.AutoMirrored.Filled.ArrowBack/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.ArrowForward/Icons.AutoMirrored.Filled.ArrowForward/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.ArrowForward/Icons.AutoMirrored.Filled.ArrowForward/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.KeyboardArrowLeft/Icons.AutoMirrored.Filled.KeyboardArrowLeft/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.KeyboardArrowLeft/Icons.AutoMirrored.Filled.KeyboardArrowLeft/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.KeyboardArrowRight/Icons.AutoMirrored.Filled.KeyboardArrowRight/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.KeyboardArrowRight/Icons.AutoMirrored.Filled.KeyboardArrowRight/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.MenuBook/Icons.AutoMirrored.Filled.MenuBook/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.MenuBook/Icons.AutoMirrored.Filled.MenuBook/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.Article/Icons.AutoMirrored.Filled.Article/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.Article/Icons.AutoMirrored.Filled.Article/g' {} +

find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Default.VolumeUp/Icons.AutoMirrored.Filled.VolumeUp/g' {} +
find app/src/main/java -name "*.kt" -exec sed -i 's/Icons.Filled.VolumeUp/Icons.AutoMirrored.Filled.VolumeUp/g' {} +
