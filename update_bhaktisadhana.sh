#!/bin/bash
sed -i 's/val activeTab = if (selectedToolSubTab == "Dashboard" ||/LaunchedEffect(selectedToolSubTab) { if (selectedToolSubTab == "Timer") viewModel.setBottomBarVisibility(false) else viewModel.setBottomBarVisibility(true) }\n\n    val activeTab = if (selectedToolSubTab == "Dashboard" ||/' app/src/main/java/com/example/ui/screens/sadhana/SadhanaScreens.kt
