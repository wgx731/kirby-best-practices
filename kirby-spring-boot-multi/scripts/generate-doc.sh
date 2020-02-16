#!/bin/bash

source ${PWD}/scripts/config.env || exit 1

$PWD/gradlew clean check jacocoTestReport javadoc asciidoctor -q || exit 2

rm -rf $PWD/build/docs
mkdir -p $PWD/build/docs

for module in ${MODULES}
do
  targetDoc="${PWD}/build/docs/${module}"
  mkdir $targetDoc
  targetJavaDoc="${PWD}/${module}/build/docs/javadoc"
  if [ -d $targetJavaDoc ]; then
    mkdir $targetDoc/java-docs
    cp -R $targetJavaDoc/* $targetDoc/java-docs || exit 2
  fi
  targetApiDoc="${PWD}/${module}/build/docs/asciidoc"
  if [ -d $targetApiDoc ]; then
    mkdir $targetDoc/api-docs
    cp -R $targetApiDoc/* $targetDoc/api-docs || exit 2
  fi
  targetTestDoc="${PWD}/${module}/build/reports/jacoco/test/html"
  if [ -d $targetTestDoc ]; then
    mkdir $targetDoc/test-docs
    cp -R $targetTestDoc/* $targetDoc/test-docs || exit 2
  fi
done
