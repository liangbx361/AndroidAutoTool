package ${PackageName}.service.api;

<#list ImportList as import>
import ${import}
</#list>

public interface ${ApiProtocolName} {

    <#list RestApiList as apiItem>
    @${apiItem.RequestMethod}("${apiItem.RequestPath}")
    ${apiItem.ModelName} ${apiItem.RequestFnName}(
        <#list apiItem.RequestParams as params>
            @${params.FieldsType}("${params.Name}") ${params.Type} ${params.Name},
        </#list>
    );

    </#list>
}
