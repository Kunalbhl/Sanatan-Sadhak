#!/bin/bash
sed -i 's/Color(0xFF2E7D32)/SadhakThemeColors.success/g' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
sed -i 's/Color(0xFFFF9800)/SadhakThemeColors.warning/g' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
sed -i 's/Color(0xFFE91E63)/SadhakThemeColors.error/g' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
sed -i 's/Color(0xFF3F51B5)/SadhakThemeColors.primary/g' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
sed -i 's/Color(0xFF673AB7)/SadhakThemeColors.secondary/g' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
sed -i 's/Color(0xFF4CAF50)/SadhakThemeColors.success/g' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
sed -i '25i import com.example.ui.theme.SadhakThemeColors' app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt
