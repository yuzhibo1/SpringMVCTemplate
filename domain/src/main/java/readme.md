## po、vo、pojo的区别，以及创建放置

### 1. PO(persistant object) 持久对象
在o/r 映射的时候出现的概念,如果没有o/r映射,就没有这个概念存在了.通常对应数据模型(数据库),本身还有部分业务逻辑的处理.可以看成是与数据库中的表相映射的java对象.最简单的PO就是对应数据库中某个表中的一条记录,多个记录可以用PO的集合.PO中应该不包含任何对数据库的操作.

> 放置跟数据库对应的实体类

### 2. VO(value object) 值对象
通常用于业务层之间的数据传递,和PO一样也是仅仅包含数据而已.但应是抽象出的业务对象,可以和表对应,也可以不,这根据业务的需要.个人觉得同DTO(数据传输对象),在web上传递.

> 跟po对应的包装类

### 3. POJO(plain ordinary java object) 简单无规则java对象
纯的传统意义的java对象.就是说在一些Object/Relation Mapping工具中,能够做到维护数据库表记录的persisent object完全是一个符合Java Bean规范的纯Java对象,没有增加别的属性和方法.我的理解就是最基本的Java Bean,只有属性字段及setter和getter方法!.

> 存放我们自己创建简单无须的类，例如导出excel配置的实体类。