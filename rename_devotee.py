import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Replace Sadhak with Devotee in specific UI strings
content = content.replace('"Sadhak Account"', '"Devotee Account"')
content = content.replace('"साधक खाता"', '"भक्त खाता"')

content = content.replace('"Create Sadhak Account"', '"Create Devotee Account"')
content = content.replace('"नया साधक खाता बनाएं"', '"नया भक्त खाता बनाएं"')

content = content.replace('"Sadhak Login"', '"Devotee Login"')
content = content.replace('"साधक प्रवेश"', '"भक्त प्रवेश"')

content = content.replace('"Sadhak Email"', '"Devotee Email"')
content = content.replace('"साधक ईमेल"', '"भक्त ईमेल"')

content = content.replace('"New to Sadhak? Create an Account"', '"New Devotee? Create an Account"')
content = content.replace('"नए साधक? खाता बनाएं"', '"नए भक्त? खाता बनाएं"')

content = content.replace('"Verified Sadhak"', '"Verified Devotee"')
content = content.replace('"सत्यापित साधक"', '"सत्यापित भक्त"')

# Fix navigation profile tab
content = content.replace('NavigationItem("Profile", if (isEnglish) "Account" else "खाता", Icons.Default.Person)', 'NavigationItem("Profile", if (isEnglish) "Devotee" else "भक्त", Icons.Default.Person)')

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    main_content = f.read()

main_content = main_content.replace('NavigationItem("Profile", if (isEnglish) "Account" else "खाता", Icons.Default.Person)', 'NavigationItem("Profile", if (isEnglish) "Devotee" else "भक्त", Icons.Default.Person)')

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(main_content)
