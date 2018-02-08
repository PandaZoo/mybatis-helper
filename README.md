### Mybatis Helper Plugin

#### 目的
在开发过程中，经常会遇到实体增加字段的时候，需要同步mapper文件，但是有时候会打错，所以想能不能写一个插件来完成这件事,于是在查阅各种资料下，很有乐趣(艰难无比)的状态下完成了初步的功能

#### 说明

目前在实体的编辑窗口中，点击plugin会弹出窗口，输入字段类型和字段名，如`BigDecimal testName`这种格式的，就会添加到这个POJO上，并且在`class.getSimpleName`基础上拼接`Mapper`的规则去寻找对应的xml文件. 找到之后会在resultMap中添加转成下划线名称的column，在sql的id含有**update**的`<set>`标签中添加该属性


#### 未完成的部分

insert部分是TagValue，所以需要用正则来替换，所以暂时没做。
希望有同学可以一起做，把日常的痛点给解决掉。


#### 不足之处

对mapper好多形式的语句只支持最简单..


#### 参考链接:

1. [https://intellij-support.jetbrains.com/hc/en-us/community/posts/207776289-How-to-change-PSIClass-java-files-](https://intellij-support.jetbrains.com/hc/en-us/community/posts/207776289-How-to-change-PSIClass-java-files-)

2. [http://www.jetbrains.org/intellij/sdk/docs/basics/psi_cookbook.html](http://www.jetbrains.org/intellij/sdk/docs/basics/psi_cookbook.html)

3. [http://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview.html](http://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview.html)

4. [http://www.jetbrains.org/intellij/sdk/docs/reference_guide/frameworks_and_external_apis/xml_dom_api.html?search=xml](http://www.jetbrains.org/intellij/sdk/docs/reference_guide/frameworks_and_external_apis/xml_dom_api.html?search=xml)

5. [http://www.jetbrains.org/intellij/sdk/docs/basics/checkout_and_build_community.html](http://www.jetbrains.org/intellij/sdk/docs/basics/checkout_and_build_community.html)
