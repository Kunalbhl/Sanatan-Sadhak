#!/bin/bash
sed -i 's/Color(0xFFFFF3E0)/SadhakThemeColors.secondaryContainer/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i 's/Color(0xFF4A0E17)/SadhakThemeColors.secondary/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i 's/Color(0xFFFFC107)/SadhakThemeColors.accent/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i 's/Color.White/SadhakThemeColors.surface/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i 's/Color(0xFF333333)/SadhakThemeColors.textPrimary/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i 's/Color(0xFFFF9800)/SadhakThemeColors.primary/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i 's/Color.Gray/SadhakThemeColors.textSecondary/g' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
sed -i '25i import com.example.ui.theme.SadhakThemeColors' app/src/main/java/com/example/ui/screens/common/CommonScreens.kt
