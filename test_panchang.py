import datetime
d = datetime.date(2026, 7, 10)
epoch = datetime.date(2020, 1, 1)
daysDiff = (d - epoch).days
print(f"daysDiff = {daysDiff}")
lunarAge = (daysDiff + 5.5) % 29.53
tithiNum = int(lunarAge / 29.53 * 30) + 1
isShukla = tithiNum <= 15
localTithiNum = tithiNum if isShukla else tithiNum - 15
print(f"lunarAge = {lunarAge}, tithiNum = {tithiNum}, localTithiNum = {localTithiNum}")
nakNum = (daysDiff * 1.05) % 27
print(f"nakNum = {nakNum}, int(nakNum) = {int(nakNum)}")
monthIndex = int((daysDiff / 29.53) + 9) % 12
print(f"monthIndex = {monthIndex}")
