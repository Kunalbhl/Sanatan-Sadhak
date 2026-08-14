import re
with open('app/src/main/java/com/example/data/AstrologyService.kt', 'r') as f:
    text = f.read()

# Fix nakNum
text = re.sub(
    r'val nakNum = \(daysDiff \* 1\.05\) % 27\s*val nakshatraVal = nakshatras\[nakNum\.toInt\(\)\]',
    '''var nakNum = (daysDiff * 1.05) % 27
            if (nakNum < 0) nakNum += 27
            val nakshatraVal = nakshatras[nakNum.toInt()]''',
    text
)

# Fix monthIndex
text = re.sub(
    r'val monthIndex = \(\(daysDiff / 29\.53\)\.toInt\(\) \+ 9\) % 12',
    '''var monthIndex = ((daysDiff / 29.53).toInt() + 9) % 12
            if (monthIndex < 0) monthIndex += 12''',
    text
)

# Fix tithiNum / lunarAge
text = re.sub(
    r'val lunarAge = \(daysDiff \+ 5\.5\) % 29\.53\s*val tithiNum = \(lunarAge / 29\.53 \* 30\)\.toInt\(\) \+ 1',
    '''var lunarAge = (daysDiff + 5.5) % 29.53
            if (lunarAge < 0) lunarAge += 29.53
            val tithiNum = (lunarAge / 29.53 * 30).toInt() + 1''',
    text
)

with open('app/src/main/java/com/example/data/AstrologyService.kt', 'w') as f:
    f.write(text)
