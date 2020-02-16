#!/bin/bash

source ${PWD}/scripts/config.env || exit 1

$PWD/gradlew clean build -x test -x spotbugsMain -x spotbugsTest -q || exit 2

rm -rf $PWD/build/jars
mkdir -p $PWD/build/jars

for module in ${MODULES}
do
  targetJar="${PWD}/${module}/build/libs/${module}-*.jar"
  if [ -f $targetJar ]; then
    cp $targetJar $PWD/build/jars || exit 2
  fi
done
