with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                val tabs = if (isEng) listOf("Karma Tracker", "Gratitude Journal") else listOf("कर्म ट्रैकर", "आभार पत्रिका")
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> KarmaTrackerView(viewModel, isEng)
                1 -> GratitudeJournalView(viewModel, isEng)
            }"""

replacement = """            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                val tabs = if (isEng) listOf("Karma Tracker", "Gratitude Journal", "Sankalpa") else listOf("कर्म ट्रैकर", "आभार पत्रिका", "संकल्प")
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> KarmaTrackerView(viewModel, isEng)
                1 -> GratitudeJournalView(viewModel, isEng)
                2 -> SankalpaJournalView(viewModel, isEng)
            }"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
