#!/bin/bash
sed -i 's/val activeTab = if (selectedToolSubTab == "Bell" ||/DisposableEffect(Unit) { onDispose { viewModel.setBottomBarVisibility(true) } }\n    val activeTab = if (selectedToolSubTab == "Bell" ||/' app/src/main/java/com/example/ui/screens/sadhana/SadhanaScreens.kt
