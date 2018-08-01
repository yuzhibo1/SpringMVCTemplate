## 此项目为奶业APP后台服务项目

### 1.特殊需求说明

### 2.开发说明
#### 2.1 开发使用的数据库配置路径

 > web/src/main/filters/dev.properties
 
 修改此文件下的配置，即可更换数据库连接


#### 2.2  开发者需要自行导入excel的jar包

>jar包中实现了一句代码导入导出excel

```
mvn install:install-file -Dfile=docs/jar/excel-1.1.1-SNAPSHOT.jar  -DgroupId=excel -DartifactId=excel -Dversion=1.1.1-SNAPSHOT -Dpackaging=jar 

```

>jar项目维护在gitlab.haomo-studio.com上的excel-jar的项目 

>使用请看的demo请看：web/src/main/test/com/haomostudio/SpringMVCTemplate/test/Test.java

