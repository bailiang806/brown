
CREATE DATABASE happydriving DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER happydriving identified by 'DrivingPlus37';
GRANT ALL ON happydriving.* TO 'happydriving'@'%';
flush privileges;