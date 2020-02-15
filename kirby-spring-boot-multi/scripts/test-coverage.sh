#!/bin/bash

MODULES="kirby-spring-boot-multi-common kirby-spring-boot-multi-api"

echo "total lines: $(find . -name "*.java" | xargs cat | grep "[a-zA-Z0-9{}]" | wc -l | tr -d ' ')"
for module in ${MODULES}
do
  echo "${module} source code lines: $(find ./${module}/src/main -name "*.java" \
    | xargs cat | grep "[a-zA-Z0-9{}]" | wc -l | tr -d ' ')"
  echo "${module} test code lines: $(find ./${module}/src/test -name "*.java" \
    | xargs cat | grep "[a-zA-Z0-9{}]" | wc -l | tr -d ' ')"
done

$PWD/gradlew clean check jacocoTestReport jacocoTestCoverageVerification -q || exit 1

for module in ${MODULES}
do
    if [ -d "${PWD}/${module}/build/reports/jacoco" ]; then
        echo "coverage report for ${module}: "
        awk -F\
        "," \
        '{ instructions += $4 + $5; covered += $5 } END \
        { print covered, "/", instructions, "instructions covered"; print 100*covered/instructions, "% covered" }' \
        ${PWD}/${module}/build/reports/jacoco/test/jacocoTestReport.csv || exit 1
    fi
done
