package cn.xunyard.idea.coding.doc;

import cn.xunyard.idea.coding.doc.logic.ProcessContext;
import cn.xunyard.idea.coding.doc.logic.builder.ParameterRender;
import cn.xunyard.idea.coding.doc.logic.builder.ResponseRender;
import cn.xunyard.idea.coding.doc.logic.describer.MethodDescriber;
import cn.xunyard.idea.coding.doc.logic.describer.ServiceDescriber;
import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.log.LoggerFactory;
import cn.xunyard.idea.coding.util.AssertUtils;
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
    private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);
    private final ProcessContext processContext;
    private final List<ServiceDescriber> serviceDescriberList;
    private final ParameterRender parameterRender = new ParameterRender();
    private final ResponseRender responseRender = new ResponseRender();

    public void run() throws IOException {
        log.info("开始生成文档...");
        String filepath = processContext.getDocConfig().getOutputDirectory() + "/" +
                processContext.getDocConfig().getOutputFileName();
        try (FileWriter fileWriter = new FileWriter(filepath, true)) {
            for (ServiceDescriber serviceDescriber : serviceDescriberList) {
                renderService(fileWriter, serviceDescriber);
            }

            log.info(String.format("文档生成完成，文档路径: %s", filepath));
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
            fileWriter.write(String.format("\n>%s\n\n", methodDescriber.getNote()));
        }

        fileWriter.write(String.format("#### 方法签名\n\n```java\n%s\n```\n", methodDescriber.getSign()));

        if (methodDescriber.hasParameter()) {
            fileWriter.write("#### 请求参数\n\n");

            parameterRender.renderParameter(fileWriter, methodDescriber.getParameterList().iterator().next());
        }


        if (methodDescriber.hasResponse()) {
            fileWriter.write("#### 返回参数\n\n");
            responseRender.renderParameter(fileWriter, methodDescriber.getResponse());
        }
    }

}
