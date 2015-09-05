package ${PackageName}.action;

<#list ImportList as import>
import ${import}
</#list>
import ${PackageName}.model.${ModelName};
import com.nd.hy.android.hermes.frame.action.Action;

public class ${ActionName} extends Action<${ModelName}> {

<#list DataList as data>
    <#if (data.Type)?? && (data.Name)??>
    private ${data.Type} ${data.Name};
    </#if>
</#list>

    public ${ActionName} () {

    }
    <#if (Params)?? >
    public ${ActionName} (${Params}) {
        <#list DataList as data>
        this.${data.Name} = ${data.Name};
        </#list>
    }
    </#if>

    @Override
    public ${ModelName} execute() throws Exception {

    }
}
