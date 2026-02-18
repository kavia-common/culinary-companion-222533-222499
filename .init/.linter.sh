#!/bin/bash
cd /home/kavia/workspace/code-generation/culinary-companion-222533-222499/recipe_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

