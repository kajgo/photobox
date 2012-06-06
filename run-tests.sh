#!/bin/sh
LOGNAME="test_$$.log"
(
    cd photobox-test &&
    ant debug install test 2>&1 | tee $LOGNAME &&
    (
        if grep '^     \[exec\] OK ([0-9]\+ tests)' $LOGNAME > /dev/null; then
            rm $LOGNAME
            exit 0
        else
            rm $LOGNAME
            echo
            echo "FAILURE: Didn't find test success part"
            exit 1
        fi
    )
)
