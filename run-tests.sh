#!/bin/sh

remove_class_files_with_no_java_file() {
    proj="$1"
    classdir="$proj/bin/classes"
    srcdir="$proj/src"
    for classfile in $(find_class_files_in "$classdir"); do
        echo "Examining $classfile"
        java_file_name=$(echo "$classfile" | sed 's/\.class/\.java/')
        java_file_full_path=$(join_paths "$srcdir" "$java_file_name")
        if [ ! -e "$java_file_full_path" ]; then
	    full_class_file=$(join_paths "$classdir" "$classfile")
            echo "Removing: $full_class_file"
            echo "       Not found: $java_file_full_path"
            rm "$full_class_file"
        fi
    done
}

find_class_files_in() {
    classdir="$1"
    if [ -e "$classdir" ]; then
        cd "$classdir" > /dev/null
        find . -name '*.class'
    fi
}

join_paths() {
    echo "$1/$2" | sed 's%\./%%g'
}

LOGNAME="test_$$.log"
(
    #remove_class_files_with_no_java_file photobox &&
    #remove_class_files_with_no_java_file photobox-test &&
    (cd photobox && ant clean) &&
    (cd photobox-test && ant clean) &&
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
