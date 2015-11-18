CAS4 Overlay Template
============================

Generic CAS maven war overlay to exercise the latest versions of CAS 4.x line. This overlay could be freely used as a starting template for local CAS maven war overlays.

# Versions
```xml
<cas.version>4.1.1</cas.version>
```

# Recommended Requirements
* JDK 1.7+

# Configuration 
The `etc` directory contains the configuration files that need to be copied to `/etc/cas`.
Or use symbolic link for development
mozanta@mozanta:/etc$ sudo ln -s /home/mozanta/projects/mozanta/idea/git/simple-cas4-overlay-template/etc/cas/

##Windows users
Copy the contents of etc/cas to C:\

###Modify the propertyFileConfigurer.xml and update the line
<util:properties id="casProperties" location="file:/etc/cas/cas.properties" />
to
<util:properties id="casProperties" location="file:C:\cas\cas.properties" />

###Modify C:\cas\jetty\jetty-ssl.xml
Search for '/etc/cas/jetty/' and replace them with 'C:\cas\jetty\'

###Modify C:\cas\cas.properties
Search for '/etc/cas/' and replace them with 'C:\cas\'


Current files are:
* `cas.properties`
* `log4j2.xml`
* `jetty/*`

# Build

```bash
mvnw clean package
```

or

```bash(Windows)
mvnw.bat clean package
```

# Deployment

## Embedded Jetty(Already done Leave it)

* Create a Java keystore at `/etc/cas/jetty/thekeystore` with the password `changeit`. 
* Import your CAS server certificate inside this keystore.

```bash
mvnw jetty:run-forked
```

```bash(Windows)
mvnw.bat jetty:run-forked
```

Debug port is 5000

CAS will be available at:

* `http://cas.server.name:8080/cas`
* `http://localhost:8080/cas`
* `https://cas.server.name:8443/cas`
* `https://localhost:8443/cas`

## External
Deploy resultant `target/cas.war` to a Servlet container of choice.
