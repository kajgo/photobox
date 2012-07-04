#!/bin/sh

if ! which adb; then
    echo "adb not found"
    exit 1
fi

pidfile=lastpid.txt

if [ -e $pidfile ]; then
    kill -9 $(cat $pidfile)
    rm $pidfile
fi

echo $$ > $pidfile

./run-tests.sh &&
(
    echo "starting app" &&
    adb shell am start -n com.photobox/.app.IntroActivity &&
    echo "app started"
) &&

rm -f $pidfile
