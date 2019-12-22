package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.logic.describer.*;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ObjectUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-21
 */
public class ServiceDocumentBuilder {
    private final DocBuildingContext docBuildingContext;
    private final List<ServiceDescriber> serviceDescriberList;

    public ServiceDocumentBuilder(DocBuildingContext docBuildingContext, List<ServiceDescriber> serviceDescriberList) {
        this.docBuildingContext = docBuildingContext;
        this.serviceDescriberList = serviceDescriberList;
    }

    public boolean run() {
        String filepath = docBuildingContext.getOutputDirectory() + "/" + docBuildingContext.getOutputFileName();
        try (FileWriter fileWriter = new FileWriter(filepath, true)) {
            for (ServiceDescriber serviceDescriber : serviceDescriberList) {
                renderService(fileWriter, serviceDescriber);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void renderService(FileWriter fileWriter, ServiceDescriber serviceDescriber) throws IOException {
        Api api = serviceDescriber.getApi();
        fileWriter.write(String.format("## %s\n\n", api.getValue()));

        if (!AssertUtils.isEmpty(api.getNote())) {
            fileWriter.write(String.format("接口说明\n%s\n", api.getNote()));
        }

        fileWriter.write(String.format("*接口路径*\n\n**%s**\n", serviceDescriber.getJavaClass().toString()));

        for (MethodDescriber methodDescriber : serviceDescriber.getMethodDescriberList()) {
            renderMethod(fileWriter, methodDescriber);
        }
    }

    private void renderMethod(FileWriter fileWriter, MethodDescriber methodDescriber) throws IOException {
        ApiOperation apiOperation = methodDescriber.getApiOperation();
        fileWriter.write(String.format("### %s\n", apiOperation.getValue()));

        if (!AssertUtils.isEmpty(apiOperation.getNote())) {
            fileWriter.write(String.format("方法说明\n%s\n\n", apiOperation.getNote()));
        }

        fileWriter.write(String.format("*方法签名*\n\n```java\n%s\n```\n", methodDescriber.getJavaMethod().toString()));
        BeanDescriber parameter = methodDescriber.getParameter();
        renderParameter(fileWriter, parameter);
    }

    private void renderParameter(FileWriter fileWriter, BeanDescriber parameter) throws IOException {
        Map<String, PropertyDescriber> propertyMap = parameter.getPropertyMap();
        fileWriter.write("\n参数名|必选|类型|描述|说明\n---|---|---|---|---\n");

        for (Map.Entry<String, PropertyDescriber> entry : propertyMap.entrySet()) {
            renderBean(fileWriter, entry.getKey(), entry.getValue());
        }
    }

    private void renderBean(FileWriter fileWriter, String name, PropertyDescriber property) throws IOException {
        fileWriter.write(name
                + "|" + (Boolean.TRUE.equals(property.getApiModelProperty().getRequired()) ? "是" : "否")
                + "|" + property.getJavaField().getType().toString()
                + "|" + ObjectUtils.firstNonNull(property.getApiModelProperty().getValue(), "-")
                + "|" + ObjectUtils.firstNonNull(property.getApiModelProperty().getNote(), "-")
                + "\n");
    }

    private void renderReturn() {

    }
}
