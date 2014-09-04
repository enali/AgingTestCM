#!/bin/bash

# usage: ./uitest.sh run
#        ./uitest.sh init

SDK_PATH=$HOME/adt/sdk
ADB_PATH=$SDK_PATH/playform-tools

# you may need to create/update build.xml
# ANDROID_PATH=$SDK_PATH/tools
# $ANDROID_PATH/android create uitest-project -n $PROJ_NAME -t 1 -p $PROJ_PATH

####    run test   ####
# git clone at $HOEM/workspace/
PROJ_NAME=AgingTestCM
PROJ_PATH=$HOME/workspace/$PROJ_NAME 
if [ "$1" = "run" ]; then
    if $(which ant >/dev/null 2>&1); then
        cd $PROJ_PATH && ant build
    elif
        # eclipse config Ant_builder:
        # right click project, Properties->Builders->New(ant builder). if you clone from github, you may not need it
        # set Name, set Buildfile( build.xml ), set Base directory, set Arguments( build ).
        # right click project's build.xml, choose Ant build
        echo "make sure you has run ant build in eclipse"
    fi

    $ADB_PATH/adb push $PROJ_PATH/bin/${PROJ_NAME}.jar /data/local/tmp/
    $ADB_PATH/adb shell uiautomator runtest ${PROJ_NAME}.jar
fi
####    end test   ####

####    init test   ####
UITEST_PHONE_PATH=/sdcard/uitest
UITEST_HOST_PATH=$PROJ_PATH/uitest
if [ "$1" = "init" ]; then
    $ADB_PATH/adb shell rm -rf $UITEST_PHONE_PATH
    $ADB_PATH/adb push $UITEST_HOST_PATH $UITEST_PHONE_PATH
fi
####    end init    ####
