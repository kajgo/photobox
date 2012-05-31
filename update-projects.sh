#!/bin/sh
android update project --name photobox --target android-10 --path photobox
android update test-project --main ../photobox --path photobox-test
