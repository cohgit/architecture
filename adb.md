adb devices
adb logcat

## grep on windows
adb logcat | FIND /I "miwom"

## list packages with text wom
adb shell sh -c 'cmd package list packages -f' | FIND /I "wom"
adb shell "run-as cl.wom.transformacion.appwommobile ls /data/app/"
adb shell netstat
adb shell am start -a android.intent.action.VIEW

## list directory contents
adb shell ls


## do not hide entries starting with
adb shell ls -a

## print index number of each file
adb shell ls -i

## print size of each file, in blocks
adb shell ls -s

## list numeric UIDs and GIDs
adb shell ls -n

## list subdirectories recursively
adb shell ls -R

## list tcp connectivity
adb shell netstat

## print current working directory location
adb shell pwd

## dumps state
adb shell dumpstate

## print process status
adb shell ps

## opens browser
adb shell am start -a android.intent.action.VIEW -d

## opened gallery
adb shell am start -t image/* -a android.intent.action.VIEW

## list users
adb shell pm list users
