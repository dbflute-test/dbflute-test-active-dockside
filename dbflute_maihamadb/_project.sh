#!/bin/bash

export ANT_OPTS=-Xmx512m

export DBFLUTE_HOME=../mydbflute/dbflute-1.x

export MY_PROPERTIES_PATH=build.properties

if [[ `uname` = "Darwin" ]]; then
  # fixed java version for #java11 test
  export JAVA_HOME=$(/usr/libexec/java_home -v 11)
fi
