import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# Remove Panchang backing states
content = re.sub(r'// Panchang & Daily Thought Backing States[\s\S]*?val currentDetails: StateFlow<String> = combine.*?5000\), "This day is auspicious for devotional activities."\)', '', content)

# Remove Panchang Source, etc.
content = re.sub(r'val panchangSource = MutableStateFlow\("Offline"\)[\s\S]*?val panchangLastUpdated = MutableStateFlow<String>\(""\)', '', content)

# Remove loadDailyPanchangAndThought (which I already cleaned up, but I can delete the rest of it)
content = re.sub(r'// --- PANCHANG & DAILY THOUGHT ACTIONS ---[\s\S]*?private var lastPanchangFetchDate = ""\s*fun loadDailyPanchangAndThought\(\) \{[\s\S]*?\}\s*\}', '', content)

content = re.sub(r'val todayDetailedPanchangForDetail = MutableStateFlow<DetailedPanchang\?>\(null\)[\s\S]*?fun loadDetailPanchang\(\) \{[\s\S]*?\}\s*\}', '', content)

content = re.sub(r'fun getDetailedPanchangForDate\([\s\S]*?\}\s*\}', '', content)

content = re.sub(r'private fun updatePanchangWithEntity[\s\S]*?\}', '', content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
