package ${PackageName}.model;

<#list ImportList as import>
import ${import}
</#list>
import com.fasterxml.jackson.annotation.JsonProperty;

public class ${ModelName} {

<#list FieldList as field>
    <#if (field.column)??>
    @Column(name = "${field.genName}")
    </#if>
    <#if (field.respFieldName)??>
    @JsonProperty("${field.respFieldName}")
    </#if>
    <#if (field.dataType)?? && (field.name)??>
    private ${field.dataType} ${field.name};
    </#if>
</#list>

<#list FieldList as field>
    <#if (field.dataType)?? && (field.name)?? && (field.fnName)??>
    public void set${field.fnName}(${field.dataType} ${field.name}) {
        this.${field.name} = ${field.name};
    }
    </#if>
    <#if (field.dataType)?? && (field.name)?? && (field.fnName)??>
    public ${field.dataType} get${field.fnName}() {
        return ${field.name};
    }
    </#if>
</#list>
}