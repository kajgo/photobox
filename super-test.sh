#!/bin/sh
# kill old

pidfile=lastpid.txt

if [ -e $pidfile ]; then
    kill -9 $(cat $pidfile)
    rm $pidfile
fi

echo $$ > $pidfile

./run-tests.sh &&
(cd photobox && adb shell am start -n com.photobox/com.photobox.PhotoboxActivity)

rm $pidfile
