#!/usr/bin/env bash
# Walks through the app on an emulator and saves a screenshot of every main screen to shots/.
set -u
PKG=com.aistudio.sanatansadhak.ksdfgh
mkdir -p shots
n=0
unanr() { for w in 1 2; do adb shell uiautomator dump /sdcard/u.xml >/dev/null 2>&1; local b; b=$(adb shell cat /sdcard/u.xml | tr '>' '\n' | grep -E 'text="(Wait|Close app)"' | head -1 | grep -o '[0-9]\+' | tr '\n' ' '); [ -z "$b" ] && return; set -- $b; adb shell input tap $(( ($1+$3)/2 )) $(( ($2+$4)/2 )); sleep 1; done; }
shot() { n=$((n+1)); sleep 3; unanr; adb exec-out screencap -p > "shots/$(printf %02d $n)-$1.png"; }
dump() { adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1; adb shell cat /sdcard/ui.xml; }
# tap the first element whose text or content-desc contains $1
tap() {
  unanr; local b; b=$(dump | tr '>' '\n' | grep -i -E "(text|content-desc)=\"[^\"]*$1" | head -1 | grep -o 'bounds="[^"]*"' | grep -o '[0-9]\+' | tr '\n' ' ')
  if [ -z "$b" ]; then echo "  (not found: $1)"; return 1; fi
  set -- $b; adb shell input tap $(( ($1+$3)/2 )) $(( ($2+$4)/2 )); echo "  tapped"; return 0
}
scroll() { adb shell input swipe 540 1700 540 500 400; }
back() { adb shell input keyevent 4; }

adb shell pm grant $PKG android.permission.POST_NOTIFICATIONS 2>/dev/null
adb shell monkey -p $PKG -c android.intent.category.LAUNCHER 1 >/dev/null
sleep 12; shot launch
for t in "Skip" "Continue as Guest" "Guest" "English" "Continue" "OK" "Allow"; do tap "$t" && sleep 3; done
shot home
scroll; shot home-2; scroll; shot home-3; scroll; shot home-4; scroll; shot home-5
for s in Knowledge Sadhana Videos Profile Home; do echo "Tab $s"; tap "$s" && { shot "tab-$s"; scroll; shot "tab-$s-scrolled"; }; done
for s in "Satsang Community" "Community" "Divine Updates" "Panchang Calendar" "AI Mitra" "Temples" "Mantra & Japa" "Dhyan"; do
  echo "Open $s"; adb shell am force-stop $PKG; adb shell monkey -p $PKG -c android.intent.category.LAUNCHER 1 >/dev/null; sleep 8
  for i in 1 2 3 4 5 6 7 8 9 10; do tap "$s" && break; scroll; sleep 1; done && { shot "open-${s// /_}"; scroll; shot "open-${s// /_}-scrolled"; back; }
done
adb logcat -d | grep -E "FATAL EXCEPTION|AndroidRuntime: (Caused|java|kotlin)|YoutubeFetcher|VideoRepository|SadhakNav" | tail -200 > shots/errors.log
echo done
