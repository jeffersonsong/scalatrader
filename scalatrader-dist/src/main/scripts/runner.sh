#!/bin/sh
set -e

# A really simplified runner for running the trading application

if [[ -z $JAVA_HOME ]]; then
    echo "ERROR: JAVA_HOME must be defined."
    exit 1
fi

JAR_FILE=$(ls -1 $(dirname $0)/scalatrader-dist-*.jar | head -1)

while getopts j:c:m: name
do
    case $name in
    j) JAR_FILE=$OPTARG;;
    e) ENV_FILE=$OPTARG;;
    m) MODE=$OPTARG;;
    ?)
    printf "Usage: %s: [-j jarfile] [-c config file]\n" $0
    exit 2;;
    esac
done

[[ ! -z $JAR_FILE ]];
[[ ! -z $ENV_FILE ]];

if [[ $MODE == "algo" ||  $MODE == "messagebroker" || $MODE == "marketdata" || $MODE == "tradereport" ]]; then
    MAIN_CLASS=net.sandipan.scalatrader.$MODE.Main
else
    echo "Mode specified ($MODE) is not a valid running mode. Must be one of: algo, messagebroker, marketdata or tradereport"
    exit 1
fi

CMD=$JAVA_HOME/bin/java -classpath $JAR_FILE $SCALA_TRADER_OPTS $MAIN_CLASS
echo "Executing: $CMD"
$CMD