package ${PackageName}.service.api;

<#list ImportList as import>
import ${import};
</#list>

public interface ${ApiProtocolName} {

    <#list RestApiList as apiItem>
    @${apiItem.RequestMethod}("${apiItem.RequestPath}")
    ${apiItem.ModelName} ${apiItem.RequestFnName}(
        <#list apiItem.RequestParams as param>
            @${param.typeForUrl}("${param.nameForUrl}") ${param.dataType} ${param.nameForFn}${param.comma}
        </#list>
    );

    </#list>
}
