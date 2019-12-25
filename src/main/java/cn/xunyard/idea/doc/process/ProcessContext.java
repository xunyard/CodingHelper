package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.process.describer.impl.SourceClassLoader;
import com.thoughtworks.qdox.JavaProjectBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@Getter
@RequiredArgsConstructor
public class ProcessContext {

    private final String serviceSuffix;
    private final String packagePrefix;
    private final String outputDirectory;
    private final String outputFileName;
    @Setter
    private Boolean logServiceDetail;
    @Setter
    private Boolean logUnresolved;
    @Setter
    private Boolean allowInfoMissing;
    @Setter
    private List<String> returnPackList;

    private SourceClassLoader sourceClassLoader;
    private ClassDescriberMaker classDescriberMaker;

    public void init() {
        sourceClassLoader = new SourceClassLoader(new JavaProjectBuilder());
        classDescriberMaker = new ClassDescriberMaker(this);
    }

}
