#! /bin/sh

jarFile=target/$(getPomAttribute.sh artifactId)-$(getPomAttribute.sh version).jar

CLASSPATH=$jarFile:$(echo target/dependency/*.jar | sed 's/ /:/g')

java -cp $CLASSPATH com.blazartech.MultiDroolsDemo.MultiDroolsDemoApplication
