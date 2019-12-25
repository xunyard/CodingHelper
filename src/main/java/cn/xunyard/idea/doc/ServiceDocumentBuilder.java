package cn.xunyard.idea.doc;

import cn.xunyard.idea.doc.process.ProcessContext;
import cn.xunyard.idea.doc.process.describer.*;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ObjectUtils;
import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-21
 */
@RequiredArgsConstructor
public class ServiceDocumentBuilder {
    private final ProcessContext processContext;
    private final List<ServiceDescriber> serviceDescriberList;


    public void run() throws IOException {
        DocLogger.info("开始生成文档...");
        String filepath = processContext.getDocConfig().getOutputDirectory() + "/" +
                processContext.getDocConfig().getOutputFileName();
        try (FileWriter fileWriter = new FileWriter(filepath, true)) {
            for (ServiceDescriber serviceDescriber : serviceDescriberList) {
                renderService(fileWriter, serviceDescriber);
            }

            DocLogger.info(String.format("文档生成完成，文档路径: %s", filepath));
        }
    }

    private void renderService(FileWriter fileWriter, ServiceDescriber serviceDescriber) throws IOException {
        fileWriter.write(String.format("## %s\n\n", serviceDescriber.getDescription()));

        if (!AssertUtils.isEmpty(serviceDescriber.getNote())) {
            fileWriter.write(String.format("接口说明\n%s\n", serviceDescriber.getNote()));
        }

        fileWriter.write(String.format("*接口路径*\n\n**%s**\n", serviceDescriber.getFullName()));

        for (MethodDescriber methodDescriber : serviceDescriber.getMethods()) {
            renderMethod(fileWriter, methodDescriber);
        }
    }

    private void renderMethod(FileWriter fileWriter, MethodDescriber methodDescriber) throws IOException {
        fileWriter.write(String.format("### %s\n", methodDescriber.getDescription()));

        if (!AssertUtils.isEmpty(methodDescriber.getNote())) {
            fileWriter.write(String.format("方法说明\n%s\n\n", methodDescriber.getNote()));
        }

        fileWriter.write(String.format("#### 方法签名\n\n```java\n%s\n```\n", methodDescriber.getSign()));

        if (methodDescriber.hasParameter()) {
            fileWriter.write("#### 请求参数\n\n");

            renderParameter(fileWriter, methodDescriber.getParameterList().iterator().next());
        }


        if (methodDescriber.hasResponse()) {
            fileWriter.write("#### 返回参数\n\n");
            renderResponse(fileWriter, methodDescriber.getResponse());
        }
    }

    private void renderParameter(FileWriter fileWriter, ParameterDescriber parameterDescriber) throws IOException {
        renderParameterClassDescriber(fileWriter, parameterDescriber.getClassDescriber());
    }

    private void renderParameterClassDescriber(FileWriter fileWriter, ClassDescriber classDescriber) throws IOException {
        fileWriter.write(String.format("**%s**\n",
                ObjectUtils.firstNonNull(classDescriber.getDescription(), classDescriber.getSimpleName())));

        if (classDescriber.hasNote()) {
            fileWriter.write(String.format("\n%s\n\n", classDescriber.getNote()));
        }

        if (classDescriber.isBasicType()) {
            fileWriter.write(String.format("%s\n\n", classDescriber.toSimpleString()));
            return;
        }

        if (classDescriber.hasFields()) {
            fileWriter.write("\n参数名|必填|类型|描述|说明\n---|---|---|---|---\n");

            for (FieldDescriber field : classDescriber.getFields()) {
                renderField(fileWriter, field);
            }
        }

        if (classDescriber.hasExtend()) {
            for (ClassDescriber describer : classDescriber.getExtend()) {
                renderParameterClassDescriber(fileWriter, describer);
            }
        }

        if (classDescriber.isParameterized()) {
            for (ClassDescriber describer : classDescriber.getParameterized()) {
                renderResponseClassDescriber(fileWriter, describer);
            }
        }
    }

    private void renderField(FileWriter fileWriter, FieldDescriber field) throws IOException {
        fileWriter.write(field.getName()
                + "|" + (field.isRequired() ? "是" : "否")
                + "|" + field.toSimpleString()
                + "|" + ObjectUtils.firstNonNull(field.getDescription(), "-")
                + "|" + ObjectUtils.firstNonNull(field.getNote(), "-")
                + "\n");
    }

    private void renderResponse(FileWriter fileWriter, ClassDescriber classDescriber) throws IOException {
        renderResponseClassDescriber(fileWriter, classDescriber);
    }

    private void renderResponseClassDescriber(FileWriter fileWriter, ClassDescriber classDescriber) throws IOException {
        fileWriter.write(String.format("**%s**\n", classDescriber.isParameterized() ? classDescriber.toSimpleString() :
                ObjectUtils.firstNonNull(classDescriber.getDescription(), classDescriber.getSimpleName())));


        if (classDescriber.hasNote()) {
            fileWriter.write(String.format("\n%s\n\n", classDescriber.getNote()));
        }

        if (classDescriber.isBasicType()) {
            fileWriter.write(String.format("%s\n\n", classDescriber.toSimpleString()));
            return;
        }

        if (classDescriber.hasFields()) {
            fileWriter.write("\n参数名|必选|类型|描述|说明\n---|---|---|---|---\n");

            for (FieldDescriber field : classDescriber.getFields()) {
                renderField(fileWriter, field);
            }
        }

        if (classDescriber.hasExtend()) {
            for (ClassDescriber describer : classDescriber.getExtend()) {
                renderResponseClassDescriber(fileWriter, describer);
            }
        }

        if (classDescriber.isParameterized()) {
            for (ClassDescriber describer : classDescriber.getParameterized()) {
                renderResponseClassDescriber(fileWriter, describer);
            }
        }
    }
}
