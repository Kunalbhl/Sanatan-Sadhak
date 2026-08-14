#!/bin/bash
sed -i 's/Color(0xFF4CAF50)/SadhakThemeColors.success/g' app/src/main/java/com/example/ui/screens/AdminControlCenterScreen.kt
sed -i 's/Color(0xFFFF9800)/SadhakThemeColors.warning/g' app/src/main/java/com/example/ui/screens/AdminControlCenterScreen.kt
sed -i 's/Color(0xFF2E7D32)/SadhakThemeColors.success/g' app/src/main/java/com/example/ui/screens/AdminControlCenterScreen.kt
sed -i 's/Color(0xFFE65100)/SadhakThemeColors.warning/g' app/src/main/java/com/example/ui/screens/AdminControlCenterScreen.kt
sed -i '15i import com.example.ui.theme.SadhakThemeColors' app/src/main/java/com/example/ui/screens/AdminControlCenterScreen.kt
