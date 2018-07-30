#!/bin/bash
mvn install:install-file -Dfile=docs/jar/plugin-1.0.0.jar -DgroupId=plugin  -DartifactId=plugin  -Dversion=1.0.0 -Dpackaging=jar