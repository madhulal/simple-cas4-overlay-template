#!/bin/bash
echo "################################################"
echo "Stopping Tomcat Server"
echo "################################################"

cd ${CATALINA_HOME}/bin
./shutdown.sh

echo "################################################"
echo "Stopped Tomcat Server"
echo "################################################"

exit