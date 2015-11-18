#!/bin/bash
echo "################################################"
echo "Starting Tomcat Server"
echo "################################################"

#To enable remote debugging
export JPDA_OPTS="-agentlib:jdwp=transport=dt_socket, address=1043, server=y, suspend=n"

cd ${CATALINA_HOME}/bin
./startup.sh

echo "################################################"
echo "Tomcat Server started"
echo "################################################"

exit