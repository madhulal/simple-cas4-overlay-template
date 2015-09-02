CAS4 Overlay
============================

Generic CAS maven war overlay to exercise the latest versions of CAS 4.x line.

# Versions
```xml
<cas.version>4.1.0-RC2</cas.version>
```

# Recommended Requirements
* JDK 1.7+
* Apache Maven 3+
* Servlet container supporting Servlet 3+ spec (e.g. Apache Tomcat 7+)
* Apache Ant 1.9+

# Configuration
The `etc` directory contains the sample configuration files that would need to be copied to an external file system location (`/etc/cas` by default)
and configured to satisfy local CAS installation needs. Current files are:

* `cas.properties`
* `log4j2.xml`

# Deployment

## Maven
* Execute `mvn clean package`
* Deploy resultant `target/cas.war` to a Servlet container of choice.

## Ant(Recommended)
* Define `CATALINA_HOME` and `MAVEN_HOME`
* Please make sure that you have ant-contrib-x.x.jar in you ANT_HOME/lib
* Execute `ant deploy`

## Start Server
* $CATALINA_HOME/bin/startup.(bat/sh)
