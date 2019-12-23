package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.describer.*;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ObjectUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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

    public void run() throws IOException {
        DocLogger.info("开始生成文档...");
        String filepath = docBuildingContext.getOutputDirectory() + "/" + docBuildingContext.getOutputFileName();
        try (FileWriter fileWriter = new FileWriter(filepath, true)) {
            for (ServiceDescriber serviceDescriber : serviceDescriberList) {
                renderService(fileWriter, serviceDescriber);
            }

            DocLogger.info(String.format("文档生成完成，文档路径: %s", filepath));
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

        fileWriter.write(String.format("#### 方法签名\n\n```java\n%s\n```\n", methodDescriber.getJavaMethod().toString()));
        BeanDescriber parameter = methodDescriber.getParameter();
        fileWriter.write("#### 请求参数\n\n");
        renderParameter(fileWriter, methodDescriber.getParameterName(), parameter);

        fileWriter.write("#### 返回参数\n\n");
        if (!AssertUtils.isEmpty(methodDescriber.getResponseList())) {
            for (BeanDescriber beanDescriber : methodDescriber.getResponseList()) {
                renderResponse(fileWriter, null, beanDescriber);
            }
        }
    }

    private void renderParameter(FileWriter fileWriter, String name, BeanDescriber parameter) throws IOException {
        fileWriter.write(String.format("**%s**\n", name));

        Map<String, PropertyDescriber> propertyMap = parameter.getPropertyMap();
        fileWriter.write("\n参数名|必选|类型|描述|说明\n---|---|---|---|---\n");

        HashMap<String, BeanDescriber> extendBeanMap = new HashMap<>();
        for (Map.Entry<String, PropertyDescriber> entry : propertyMap.entrySet()) {
            PropertyDescriber propertyDescriber = entry.getValue();
            String fieldName = entry.getKey();
            renderField(fileWriter, entry.getKey(), propertyDescriber);

            if (propertyDescriber.getExtendBean() != null) {
                extendBeanMap.put(fieldName, propertyDescriber.getExtendBean());
            }
        }

        for (Map.Entry<String, BeanDescriber> entry : extendBeanMap.entrySet()) {
            renderParameter(fileWriter, entry.getKey(), entry.getValue());
        }
    }

    private void renderField(FileWriter fileWriter, String name, PropertyDescriber property) throws IOException {
        fileWriter.write(name
                + "|" + (Boolean.TRUE.equals(property.getApiModelProperty().getRequired()) ? "是" : "否")
                + "|" + property.getJavaField().getType().getValue()
                + "|" + ObjectUtils.firstNonNull(property.getApiModelProperty().getValue(), "-")
                + "|" + ObjectUtils.firstNonNull(property.getApiModelProperty().getNote(), "-")
                + "\n");
    }

    private void renderResponse(FileWriter fileWriter, String name, BeanDescriber beanDescriber) throws IOException {
        if (!AssertUtils.isEmpty(name)) {
            fileWriter.write(String.format("**%s**\n", name));
        }

        if (beanDescriber == null) {
            return;
        }

        if (beanDescriber.isBasicType()) {
            fileWriter.write(beanDescriber.getJavaType().toString() + "\n\n");
            return;
        }

        Map<String, PropertyDescriber> propertyMap = beanDescriber.getPropertyMap();
        fileWriter.write("\n参数名|非空|类型|描述|说明\n---|---|---|---|---\n");
        HashMap<String, BeanDescriber> extendBeanMap = new HashMap<>();
        for (Map.Entry<String, PropertyDescriber> entry : propertyMap.entrySet()) {
            PropertyDescriber propertyDescriber = entry.getValue();
            String fieldName = entry.getKey();
            renderField(fileWriter, entry.getKey(), propertyDescriber);

            if (propertyDescriber.getExtendBean() != null) {
                extendBeanMap.put(fieldName, propertyDescriber.getExtendBean());
            }
        }

        for (Map.Entry<String, BeanDescriber> entry : extendBeanMap.entrySet()) {
            renderResponse(fileWriter, entry.getKey(), entry.getValue());
        }
    }
}
