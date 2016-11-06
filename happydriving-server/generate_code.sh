#!/bin/sh
mvn mybatis-generator:generate
#since classpath*: can resolve resource problem, the code below is no use...
cp src/main/java/com/happydriving/rockets/server/domain/* web/WEB-INF/config/mapper/