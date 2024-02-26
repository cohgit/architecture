
db pull '/storage/emulated/0/Download/Sesiones de Trabajo. IBS a la Nube-20230118_080858-Grabación de la reunión.zip.002'



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

## Connect to device by scrcpy USB
>adb devices
List of devices attached
5200ccfae28a442f        device

https://github.com/Genymobile/scrcpy
>scrcpy -s 5200ccfae28a442f


## Connect to device by scrcpy Wifi
>adb devices
List of devices attached
5200ccfae28a442f	device
R5CR50X9ZSK	device

### activate port 5555 for service adb
>adb -s 5200ccfae28a442f tcpip 5555

### view ip wlan
>adb -s 5200ccfae28a442f shell ip addr show wlan0

### connect to ip and port
>adb connect 192.168.1.91:5555
>adb shell input keyevent KEYCODE_HOME

### view phone
>scrcpy -s 192.168.1.91:5555
>scrcpy -s 5200ccfae28a442f