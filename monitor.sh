#!/bin/sh
~/.cabal/bin/codemonitor <<EOF
.
tests \.java$ sh super-test.sh
EOF
