package ${PackageName}.model;

<#list ImportList as import>
import ${import}
</#list>

public class ${ModelName} {

<#list FieldsList as fields>
    <#if (fields.column)??>
    @Column(name = "${fields.name}")
    </#if>
    <#if (fields.type)?? && (fields.name)??>
    private ${fields.type} ${fields.name};
    </#if>
</#list>

<#list FieldsList as fields>
    <#if (fields.type)?? && (fields.name)?? && (fields.fnName)??>
    public void set${fields.fnName}(${fields.type} ${fields.name}) {
        this.${fields.name} = ${fields.name};
    }
    </#if>
    <#if (fields.type)?? && (fields.name)?? && (fields.fnName)??>
    public ${fields.type} get${fields.fnName}() {
        return ${fields.name};
    }
    </#if>
</#list>
}