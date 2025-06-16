#!/bin/bash

mvn clean test \
    -Dgroups="test" \
    -Dbrowser="$1" \
    -Dmode="fullscreen" \
    -DbrowserVersion="$2" \
    -Dremote.url="http://193.104.57.173/wd/hub"