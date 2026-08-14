#!/bin/bash
sed -i 's/val activeTab = if (selectedToolSubTab == "Bell" ||/LaunchedEffect(selectedToolSubTab) { if (selectedToolSubTab == "Counter") viewModel.setBottomBarVisibility(false) else viewModel.setBottomBarVisibility(true) }\n\n    val activeTab = if (selectedToolSubTab == "Bell" ||/' app/src/main/java/com/example/ui/screens/sadhana/SadhanaScreens.kt
