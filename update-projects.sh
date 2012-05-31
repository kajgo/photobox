#!/bin/sh
android update project --name photobox --target android-8 --path photobox
android update test-project --main ../photobox --path photobox-test
