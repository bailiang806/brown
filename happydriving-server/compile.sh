#!/bin/sh


#---master---
cp src/main/resources/Weixin_prod.properties src/main/resources/Weixin.properties
mvn clean package
scp -r src/main/webapp/images root@www.ejiapei.com:/home/ejiapei/service/fileservice/
scp -r src/main/webapp/img root@www.ejiapei.com:/home/ejiapei/service/fileservice/
scp -r src/main/webapp/js root@www.ejiapei.com:/home/ejiapei/service/fileservice/
scp -r src/main/webapp/css root@www.ejiapei.com:/home/ejiapei/service/fileservice/
scp -r src/main/webapp/Restructures root@www.ejiapei.com:/home/ejiapei/service/fileservice/
scp target/happydriving.war root@www.ejiapei.com:/home/ejiapei/service/apache-tomcat-7.0.54/webapps/
sleep 15
ssh root@www.ejiapei.com 'bash /home/ejiapei/service/apache-tomcat-7.0.54/bin/restart-server.sh'

#---develop---
#cp src/main/resources/Weixin_dev.properties src/main/resources/Weixin.properties
#mvn clean package
#scp -r src/main/webapp/images root@test.ejiapei.com:/home/ejiapei/service/fileservice/
#scp -r src/main/webapp/img root@test.ejiapei.com:/home/ejiapei/service/fileservice/
#scp -r src/main/webapp/js root@test.ejiapei.com:/home/ejiapei/service/fileservice/
#scp -r src/main/webapp/css root@test.ejiapei.com:/home/ejiapei/service/fileservice/
#scp -r src/main/webapp/Restructures root@test.ejiapei.com:/home/ejiapei/service/fileservice/
#scp target/happydriving.war root@test.ejiapei.com:/home/ejiapei/service/apache-tomcat-7.0.54/webapps/
#sleep 15
#ssh root@test.ejiapei.com 'bash /home/ejiapei/service/apache-tomcat-7.0.54/bin/restart-server.sh'
