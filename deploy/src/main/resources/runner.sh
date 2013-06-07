#!/bin/sh
set -e

# A really simplified runner for the trading application

JAR_FILE=""
ENV_FILE=""

while getopts j:c: name
do
    case $name in
    j) JAR_FILE=$OPTARG;;
    e) ENV_FILE=$OPTARG;;
    ?)
    printf "Usage: %s: [-j jarfile] [-c config file]\n" $0
    exit 2;;
    esac
done

[[ ! -z $JAR_FILE ]];
[[ ! -z $ENV_FILE ]];

java