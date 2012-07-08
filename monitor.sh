#!/bin/sh
~/.cabal/bin/codemonitor <<EOF
.
tests \.xml$|\.java$ sh super-test.sh
EOF
