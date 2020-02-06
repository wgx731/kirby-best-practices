#!/bin/bash

$PWD/gradlew clean build -x test -x spotbugsMain -x spotbugsTest -q || exit 1
