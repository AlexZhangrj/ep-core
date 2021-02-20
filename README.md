## 由于JsonResult实质是一个Map，默认SpringMVC的Controller不能处理Map数据类型的返回值为Json数据

## 解决方法有两种：

1. 在Controller的方法上加两个返回值的标注：
@ResponseBody
@ResponseJsonWithFilter
或
@ResponseBody
@ResponseJson

2. 对SpringMVC框架做一个hack，然后，只使用：@ResponseJsonWithFilter或@ResponseJson标注，有需要的email我：zhangrenjie1981@126.com

## 使用方式，参照：[ExampleController](https://github.com/AlexZhangrj/ep-core/blob/master/src/main/java/com/zhrenjie04/alex/example/ExampleController.java)

## 在本的架构代码中，遵循以下规则：

1. 包括辅助属性，所有的属性都写入Entity；

2. 通过@ResponseJsonWithFilter控制给前端或其他微服务输出数据时包含的字段；

3. 通过@ResponseJson输出所有字段；

4. 通过@ResponseJsonWithFilters控制多种实体类的输出字段；

5. 所有xxxVo都继承对应的Entity；

6. 使用xxxVo的唯一目的：只用来适配输入验证框架。

Vo使用例子，参考：ExampleController、UserVoForExampleRegister。