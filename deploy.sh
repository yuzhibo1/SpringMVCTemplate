#!/bin/bash
#rsync -avz web/target/SpringMVCTemplate.war  member@tentop.com.cn:/opt/tomcat8/webapps/SpringMVCTemplate.war
#rsync -avz web/target/SpringMVCTemplate.war  member@haomo-tech.com:/data/tomcat/webapps/SpringMVCTemplate.war

rsync -avz -e "ssh -p12322" web/target/SpringMVCTemplate.war haomo@haomo-studio.com:/opt/hm_tomcat/webapps