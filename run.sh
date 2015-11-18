#!/bin/bash
clear

pwd=$(pwd)
echo "Current directory ${pwd}"


echo "################################################"
echo "Building CAS War"
echo "################################################"
cd ${pwd}
mvn package


echo "################################################"
echo "Stopping tomcat"
echo "################################################"
#stop tomcat
cd ${pwd}
./stopTomcat.sh

echo "################################################"
echo "Clear the existing war"
echo "################################################"
cd ${CATALINA_HOME}/webapps/
rm -rf mozanta_cas
rm -f mozanta_cas.war

echo "################################################"
echo "Copying the war to tomcat"
echo "################################################"
cd ${pwd}/target
cp mozanta_cas.war ${CATALINA_HOME}/webapps/


echo "################################################"
echo "Clear the logs director"
echo "################################################"
cd ${CATALINA_HOME}/logs
rm -rf *

echo "################################################"
echo "Starting tomcat"
echo "################################################"
#start tomcat
cd ${pwd}
./startTomcat.sh