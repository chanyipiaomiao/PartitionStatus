#!/bin/bash

if /usr/bin/which java &>/dev/null; then
	java -jar getDiskUsage.jar
else
	echo "Java not Found,Please install JDK !!!"
fi
