[#ftl]
[@b.head/]
[@b.toolbar title="修改数据类型"]bar.addBack("${b.text("action.back")}");[/@]
[@b.form action="!save" title="数据类型" theme="list"]
  [@b.textfield label="common.name" name="dataType.name" value="${dataType.name!}" required="true" maxlength="50"/]
  [@b.textfield label="类型" name="dataType.typeName" value="${dataType.typeName!}" required="true" maxlength="200" style="width:300px" comment="java数据类型,需要有默认构造函数"/]
  [@b.textfield label="关键字" name="dataType.keyName" value="${dataType.keyName!}" required="true" maxlength="50"/]
  [@b.textfield label="其他属性" name="dataType.properties" value="${(dataType.properties)!}" style="width:300x;" maxlength="100" comment="多个属性用,分割"/]
  [@b.formfoot]
    <input type="hidden" name="dataType.id" value="${(dataType.id)!}"/>
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
  [/@]
</table>
[/@]
[@b.foot/]