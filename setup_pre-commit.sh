#!/bin/bash
git config --add checkstyle.jar  ./pre-commit/checkstyle-8.14-all.jar
git config --add checkstyle.checkfile  ./pre-commit/google_checks.xml
cp ./pre-commit/pre-commit .git/hooks/
echo "pre-commit set up success!"
