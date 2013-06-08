#!/bin/bash

# A really simplified runner for running any part of the trading application

if [[ -z $JAVA_HOME ]]; then
    echo "ERROR: JAVA_HOME must be defined."
    exit 1
fi

function usage_msg() {
    printf "Usage: %s: [options]\n" $0
    echo "Options available are:"
    echo "- [-m mode]: Required. Mode must be one of: algo, messagebroker, marketdata or tradereport"
    echo "- [-j jarfile]: Optional. Specify the jar file manually. Default is $JAR_FILE"
    echo "- [-c config]: Optional. Specify a custom config (an example config is provided in ../conf/)."
    echo "               By default, the application.conf within the jar file is what's used (identical to the example config)."
    echo ""
    echo "Misc:"
    echo "- You can also set SCALA_TRADER_OPTS to pass extra options, for instance JVM parameters"

}

JAR_FILE=$(ls -1 $(dirname $0)/scalatrader-dist-*.jar | head -1)
CONFIG_FILE=""
MODE=""

while getopts j:c:m: name
do
    case $name in
    j) JAR_FILE=$OPTARG;;
    e) CONFIG_FILE=$OPTARG;;
    m) MODE=$OPTARG;;
    ?)
    usage_msg
    exit 2;;
    esac
done

if [[ $MODE == "algo" ||  $MODE == "messagebroker" || $MODE == "marketdata" || $MODE == "tradereport" ]]; then
    MAIN_CLASS=net.sandipan.scalatrader.$MODE.Main
else
    echo "Mode specified ($MODE) is not a valid running mode."
    usage_msg
    exit 1
fi

SCALA_TRADER_OPTS=$SCALA_TRADER_OPTS
if [[ $CONFIG_FILE != "" ]]; then
    SCALA_TRADER_OPTS="-Dconfig.file=$CONFIG_FILE $SCALA_TRADER_OPTS"
fi

CMD="$JAVA_HOME/bin/java -classpath $JAR_FILE $SCALA_TRADER_OPTS $MAIN_CLASS"
echo "Executing: $CMD"
$CMD