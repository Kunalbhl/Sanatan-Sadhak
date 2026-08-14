import sys
content = sys.stdin.read()
target = """val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y, hh, mm, ss)"""
replacement = """// Use 6:00 AM (Sunrise approximation) to get the day's standard Tithi, just like Calendar
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y, 6, 0, 0)"""
if target in content:
    with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
        f.write(content.replace(target, replacement))
    print("Fixed Panchang time in viewmodel")
else:
    print("Target not found in viewmodel")
