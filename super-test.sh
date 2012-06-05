#!/bin/sh
./run-tests.sh &&
(cd photobox && adb shell am start -n com.photobox/com.photobox.PhotoboxActivity)
