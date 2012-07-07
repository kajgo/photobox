#!/bin/bash

# This script assumes that environment variables are set up in .travis.yml.

function log() {
    echo "~~ $1 ~~"
}

log 'starting xvfb'
sh -e /etc/init.d/xvfb start

log 'downloading sdk'
wget http://dl.google.com/android/android-sdk_r18-linux.tgz

log 'unzipping sdk'
tar xfz android-sdk_r18-linux.tgz

log 'installing additional sdk components'
android update sdk --no-ui --filter tools,platform-tools,android-12

log 'listing targets'
android list targets

log 'creating device'
echo no | android create avd -n test -t android-12 --force

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
