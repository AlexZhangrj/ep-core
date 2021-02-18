由于JsonResult实质是一个Map，默认SpringMVC的Controller不能处理
Map数据类型的返回值为Json数据，需要对SpringMVC框架做一个hack，有
需要的email我：zhangrenjie1981@126.com

使用方式，参照：ExampleController

在我的架构代码中，遵循以下规则：
1、包括辅助属性，所有的属性都写入Entity；
2、通过@ResponseJsonWithFilter控制给前端或其他微服务输出数据时包含的字段；
3、通过@ResponseJson输出所有字段；
4、通过@ResponseJsonWithFilters控制多种实体类的输出字段；
5、所有xxxVo都继承对应的Entity；
6、使用xxxVo的唯一目的：只用来适配输入验证框架。

Vo使用例子，参考：ExampleController、UserVoForExampleRegister。