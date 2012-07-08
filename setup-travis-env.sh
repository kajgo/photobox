#!/bin/bash

# This script assumes that environment variables are set up in .travis.yml.

function log() {
    echo "~~ $1 ~~"
}

ANDROID_VERSION="android-8"
SDK_NAME="android-sdk_r20-linux.tgz"

log 'starting xvfb'
sh -e /etc/init.d/xvfb start

log 'downloading sdk'
wget http://dl.google.com/android/$SDK_NAME

log 'unzipping sdk'
tar xfz $SDK_NAME

log 'listing extended'
android list sdk --extended

log 'installing additional sdk components'
android update sdk --no-ui --filter tools,platform-tools,$ANDROID_VERSION

log 'listing targets'
android list targets

log 'creating device'
echo no | android create avd -n test -t $ANDROID_VERSION --force

log 'starting emulator'
nohup emulator -avd test &

log 'log: adb devices'
adb devices

while ! (adb devices | grep '^emulator-.*device$'); do
    log 'waiting for emulator to start'
    sleep 5
done

log 'waiting some more (30s) just to be sure'
sleep 30

log 'log: adb devices'
adb devices

log 'log: nohup.out'
cat nohup.out

log 'updating projects'
sh update-projects.sh
