#!/bin/bash
#rsync -avz web/target/milk.war  member@tentop.com.cn:/opt/tomcat8/webapps/milk.war
#rsync -avz web/target/milk.war  member@haomo-tech.com:/data/tomcat/webapps/milk.war

rsync -avz -e "ssh -p12322" web/target/milk.war haomo@haomo-studio.com:/opt/hm_tomcat/webapps