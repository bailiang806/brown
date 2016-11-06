#rm -r src/main/webapp/Restructures/controller
#mv src/main/webapp/Restructures/controller_bak src/main/webapp/Restructures/controller

#rm -r src/main/webapp/Restructures/router
#mv src/main/webapp/Restructures/router_bak src/main/webapp/Restructures/router

#rm -r src/main/webapp/Restructures/templates
#mv src/main/webapp/Restructures/templates_bak src/main/webapp/Restructures/templates

#rm -r src/main/webapp/Restructures/imgs
#mv src/main/webapp/Restructures/imgs_bak src/main/webapp/Restructures/imgs




echo 正在进行前端文件预处理....

echo 正在压缩js文件....
gulp controllerjs
gulp routerjs

echo 正在压缩css文件....
gulp effectcss

echo 正在压缩html文件
gulp htmlprogress

echo 正在压缩图片....
gulp imgscompress

echo 正在替换文件操作.....
mv src/main/webapp/Restructures/controller src/main/webapp/Restructures/controller_bak
mv src/main/webapp/Restructures/router src/main/webapp/Restructures/router_bak
mv src/main/webapp/Restructures/effect/css src/main/webapp/Restructures/effect/css_bak
mv src/main/webapp/Restructures/templates src/main/webapp/Restructures/templates_bak
mv src/main/webapp/Restructures/imgs src/main/webapp/Restructures/imgs_bak

cp -r src/main/webapp/temp/controller src/main/webapp/Restructures
cp -r src/main/webapp/temp/router src/main/webapp/Restructures
cp -r src/main/webapp/temp/effect/css src/main/webapp/Restructures/effect
cp -r src/main/webapp/temp/templates src/main/webapp/Restructures
cp -r src/main/webapp/temp/imgs src/main/webapp/Restructures







