#!/bin/sh
(cd photobox && ant clean)
(cd photobox-test && ant clean)
adb uninstall com.photobox
adb uninstall com.photobox.test
