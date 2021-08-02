/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.maven.plugin;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import fast.mock.test.core.constant.CommonConstant;
import fast.mock.test.core.entity.ConfigEntity;
import fast.mock.test.core.info.JavaClassInfo;
import fast.mock.test.core.log.MySystemStreamLog;
import fast.mock.test.core.util.PackageUtils;
import fast.mock.test.core.util.StringUtils;
import fast.mock.test.maven.plugin.base.AbstractPlugin;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.artifact.filter.StrictPatternExcludesArtifactFilter;
import org.apache.maven.shared.artifact.filter.StrictPatternIncludesArtifactFilter;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.filter.AncestorOrSelfDependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.filter.AndDependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.filter.ArtifactDependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.filter.DependencyNodeFilter;
import org.apache.maven.shared.dependency.graph.traversal.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * 构建自动化测试
 *
 * @author chenhx
 * @version UnittestPlugin.java, v 0.1 2019-05-30 17:47 chenhx
 */
@Mojo(name = "test")
public class UnittestPlugin extends AbstractPlugin {

    private Log log = new MySystemStreamLog();
    /**
     * 需要测试的项目包名
     */
    @Parameter
    private String testPackageName;
    /**
     * 作者
     */
    @Parameter(defaultValue = "")
    private String author;

    /**
     * 是否获取子包下的类
     */
    @Parameter(defaultValue = "true")
    private Boolean isGetChildPackage;

    /**
     * 配置是否mock掉父类以及自身测试类非测试的方法(默认true)
     */
    @Parameter(defaultValue = "true")
    private Boolean isMockThisOtherMethod;

    /**
     * 配置是否设置基础类型的值随机生成(默认false)
     */
    @Parameter(defaultValue = "false")
    private Boolean isSetBasicTypesRandomValue;

    /**
     * 配置字符串随机值的位数（例如："10"，表示10位随机字母/数字字符）
     * V1.1.2+
     */
    @Parameter(defaultValue = "10")
    private String setStringRandomRange;
    /**
     * 配置int/Integer类型随机值的范围（例如："0,1000"，表示[0,1000)范围的int数值，配置固定的值可配置为"0",则int值固定为0）
     * V1.1.2+
     */
    @Parameter(defaultValue = "0,1000")
    private String setIntRandomRange;
    /**
     * 配置long/Long类型随机值的范围(配置规则与setIntRandomRange类似)
     * V1.1.2+
     */
    @Parameter(defaultValue = "0,10000")
    private String setLongRandomRange;
    /**
     * 配置boolean/Boolean类型随机值的范围（例如：配置为"true"/"false"表示为固定的值，其他任意值表示true和false随机）
     * V1.1.2+
     */
    @Parameter(defaultValue = "true,false")
    private String setBooleanRandomRange;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject mavenProject;

    /**
     * The Maven project.
     */
    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Parameter( defaultValue = "${session}", readonly = true, required = true )
    private MavenSession session;

    @Parameter( property = "scope" )
    private String scope;

    /**
     * The dependency tree builder to use.
     */
    @Component( hint = "default" )
    private DependencyGraphBuilder dependencyGraphBuilder;
    /**
     * The computed dependency tree root node of the Maven project.
     */
    private DependencyNode rootNode;
    /**
     * Contains the full list of projects in the reactor.
     */
    @Parameter( defaultValue = "${reactorProjects}", readonly = true, required = true )
    private List<MavenProject> reactorProjects;
    @Parameter( property = "tokens", defaultValue = "standard" )
    private String tokens;
    @Parameter( property = "includes" )
    private String includes;
    @Parameter( property = "excludes" )
    private String excludes;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            long startTime = System.currentTimeMillis();
            //设置配置值
            super.execute();

            //初始化的一些操作
            init();

            log.info("开始生成自动化测试Case+Mock代码，请稍等片刻。");
            log.debug("数据初始化后，开始生成自动化测试代码" + "\n开发者的配置数据：" + CommonConstant.CONFIG_ENTITY);

            //参数校验
            if (!paramCheck()) {
                return;
            }


            //记录每个包名下的所有类,key-包名，value-所有类的集合
            Map<String, List<String>> javaListMap = new HashMap<>();
            //如果配置的是java类，直接使用该类
            testPackageName = testPackageName.trim();
            String typeName = null; // class类型名称
            if (testPackageName.endsWith(".java")) {
                //单类其实只是为了方便测试类中方法的生成，并不会进行文档说明使用
                List<String> javaList = new ArrayList<>();
                typeName = testPackageName;
                typeName = typeName.substring(0, typeName.lastIndexOf("."));
                typeName = typeName.substring(typeName.lastIndexOf(".") + 1, typeName.length());

                testPackageName = testPackageName.substring(0, testPackageName.lastIndexOf(".java"));
                String classPathName = basedir + "/src/main/java/" + testPackageName.replace(".", "/") + ".java";
                log.info("待生成单元测试类代码的类路径为："+classPathName);
                javaList.add(classPathName);
                javaListMap.put(testPackageName.substring(0, testPackageName.lastIndexOf(".")), javaList);
            } else {

                //遍历包名，多个包的情况
                if (testPackageName.contains(";")) {
                    String[] packageNames = testPackageName.split(";");
                    for (String packageName : packageNames) {
                        if (StringUtils.isEmpty(packageName)) {
                            continue;
                        }
                        //换行，空格去除
                        packageName = packageName.replaceAll("\\r|\\n", "").trim();
                        //获取该包下所有的类
                        List<String> javaList = PackageUtils.getClassName(basedir.getPath(), packageName, isGetChildPackage);
                        javaListMap.put(packageName, javaList);
                    }

                } else {
                    //获取该包下所有的类
                    List<String> javaList = PackageUtils.getClassName(basedir.getPath(), testPackageName, isGetChildPackage);
                    javaListMap.put(testPackageName, javaList);
                }

            }

            //遍历javaListMap，需要生成测试类的包
            for (String packageName : javaListMap.keySet()) {
                //设置当前进行生成类的测试包名
                List<String> javaList = javaListMap.get(packageName);
                log.info("当前遍历的包名为：" + packageName );
                //class文件绝对路径
                for (String javaNamePath : javaList) {
                    log.info("当前java文件的绝对路径为：" + javaNamePath);
                    if (javaNamePath.endsWith("$1")) {
                        //跳过
                        log.info("跳过内部类的生成:" + javaNamePath);
                        continue;
                    }
                    //准备参数值
                    JavaClassInfo javaClassInfo = new JavaClassInfo();

                    javaClassInfo.setPackageName(packageName);
    //                if(StringUtil.isNotEmpty( CommonConstant.CONFIG_ENTITY.getTestClassPackageName() )){
    //                    javaClassInfo.setPackageName(CommonConstant.CONFIG_ENTITY.getTestClassPackageName());
    //                }

                    javaClassInfo.setAbsolutePath(javaNamePath);
                    javaClassInfo.setFullyTypeName(javaClassInfo.getPackageName() + "." + javaClassInfo.getTypeName());

                    //生成测试类文件路径   被测试类的绝对路径
                    String testJavaName = CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + javaClassInfo.getTypeName() + CommonConstant.TEST_CLASS_SUFFIX + ".java";
                    javaClassInfo.setTestAbsolutePath(testJavaName);
                    javaClassInfo.setMockAbsolutePath(CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + javaClassInfo.getTypeName()+CommonConstant.MOCK_CLASS_SUFFIX + ".java");

                    if (null != typeName) {
                        javaClassInfo.setFullyTypeName(javaClassInfo.getPackageName() + "." + typeName);
                        javaClassInfo.setTestAbsolutePath(CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + typeName+CommonConstant.TEST_CLASS_SUFFIX + ".java");
                        javaClassInfo.setMockAbsolutePath(CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + typeName+CommonConstant.MOCK_CLASS_SUFFIX + ".java");
                        javaClassInfo.setTypeName(typeName);
                    }
                    log.debug("生成的类信息javaClassInfo：" + JSON.toJSONString(javaClassInfo));

                    //核心
                    GenJava.genTest(javaClassInfo);
                }
            }
            double timeout = (System.currentTimeMillis()-startTime)/1000D;
            log.info("成功生成自动化测试Case+Mock代码！耗时："+ timeout +"秒");
        } finally {
            //最终的一些操作
            DownFile.removeTemplateFile();
        }
    }

    /**
     * 初始化操作
     */
    private void init() {
        CommonConstant.CONFIG_ENTITY.setTestPackageName(testPackageName);
        CommonConstant.CONFIG_ENTITY.setAuthor(author);
        CommonConstant.CONFIG_ENTITY.setIsGetChildPackage(isGetChildPackage);
        CommonConstant.CONFIG_ENTITY.setIsMockThisOtherMethod(isMockThisOtherMethod);
        CommonConstant.CONFIG_ENTITY.setIsSetBasicTypesRandomValue(isSetBasicTypesRandomValue);
        CommonConstant.CONFIG_ENTITY.setSetStringRandomRange(setStringRandomRange);
        CommonConstant.CONFIG_ENTITY.setSetBooleanRandomRange(setBooleanRandomRange);
        CommonConstant.CONFIG_ENTITY.setSetIntRandomRange(setIntRandomRange);
        CommonConstant.CONFIG_ENTITY.setSetLongRandomRange(setLongRandomRange);

        log.debug("init params："+ JSON.toJSONString(CommonConstant.CONFIG_ENTITY));

        //初始化json文件
        DownFile.downJsonFile();

        //下载配置文件
        DownFile.downTemplateFile();

        //初始化类源,加载类信息
        initJavaProjectBuilder();
    }

    /**
     * 参数校验
     *
     * @return 校验结果，true-校验通过，false-校验失败
     */
    private boolean paramCheck() {
        if (StringUtils.isEmpty(testPackageName)) {
            getLog().error("testPackageName必须进行配置（需要测试的项目包名）");
            return false;
        }
        return true;
    }

    /**
     * 初始化类源
     */
    private void initJavaProjectBuilder() {
        try {
            String mainJava = basedir.getPath() + CommonConstant.JAVA_MAIN_SRC;
            log.info("加载当前模块的类：" + mainJava);
            CommonConstant.javaProjectBuilder.addSourceTree(new File(mainJava));
        } catch (Exception e) {
            log.error("读取包下所有的java类文件 异常" + e.getMessage());
        }
        try {
            String testJava = basedir.getPath() + CommonConstant.JAVA_TEST_SRC;
            log.info("加载当前模块的测试类：" + testJava);
            CommonConstant.javaProjectBuilder.addSourceTree(new File(testJava));
        } catch (Exception e) {
            log.error("读取包下所有的测试的java类文件 异常" + e.getMessage());
        }
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            URL[] urls = ((URLClassLoader) cl).getURLs();

            URL[] packageUrls = getPackageUrls();
            CommonConstant.urlClassLoader = new URLClassLoader(packageUrls, Thread.currentThread().getContextClassLoader());
            if (CommonConstant.urlClassLoader != null) {
                CommonConstant.javaProjectBuilder.addClassLoader(CommonConstant.urlClassLoader);
            }
        } catch (Exception e) {
            log.error("类加载异常", e);
        }
    }

    private URL[] getPackageUrls() throws MojoExecutionException {
        List<URL> runtimeUrls = new ArrayList<>();
        try
        {
            String dependencyTreeString;

            // TODO: note that filter does not get applied due to MSHARED-4
            ArtifactFilter artifactFilter = createResolvingArtifactFilter();

            ProjectBuildingRequest buildingRequest =
                    new DefaultProjectBuildingRequest( session.getProjectBuildingRequest() );

            buildingRequest.setProject( project );

            // non-verbose mode use dependency graph component, which gives consistent results with Maven version
            // running
            rootNode = dependencyGraphBuilder.buildDependencyGraph( buildingRequest, artifactFilter,
                    reactorProjects );

            recursionAddUrl(runtimeUrls, rootNode.getChildren());

            System.out.println("");
            //dependencyTreeString = serializeDependencyTree( rootNode );

        }
        catch ( Exception exception ){
            throw new MojoExecutionException( "Cannot build project dependency graph", exception );
        }


        return runtimeUrls.toArray(new URL[runtimeUrls.size()]);
    }

    private void recursionAddUrl(List<URL> runtimeUrls, List<DependencyNode> children) throws MalformedURLException {
        for (DependencyNode childTmp : children) {
            URL url = childTmp.getArtifact().getFile().toURI().toURL();
            runtimeUrls.add(url);
            recursionAddUrl(runtimeUrls, childTmp.getChildren());
        }
    }


    private ArtifactFilter createResolvingArtifactFilter() {
        ArtifactFilter filter;

        // filter scope
        if (scope != null) {
            getLog().debug("+ Resolving dependency tree for scope '" + scope + "'");

            filter = new ScopeArtifactFilter(scope);
        } else {
            filter = null;
        }

        return filter;
    }

    /**
     * @param writer {@link Writer}
     * @return {@link DependencyNodeVisitor}
     */
    public DependencyNodeVisitor getSerializingDependencyNodeVisitor(Writer writer) {
        return new SerializingDependencyNodeVisitor(writer, toGraphTokens(tokens));
    }

    /**
     * Gets the graph tokens instance for the specified name.
     *
     * @param theTokens the graph tokens name
     * @return the <code>GraphTokens</code> instance
     */
    private SerializingDependencyNodeVisitor.GraphTokens toGraphTokens(String theTokens) {
        SerializingDependencyNodeVisitor.GraphTokens graphTokens;

        if ("whitespace".equals(theTokens)) {
            getLog().debug("+ Using whitespace tree tokens");

            graphTokens = SerializingDependencyNodeVisitor.WHITESPACE_TOKENS;
        } else if ("extended".equals(theTokens)) {
            getLog().debug("+ Using extended tree tokens");

            graphTokens = SerializingDependencyNodeVisitor.EXTENDED_TOKENS;
        } else {
            graphTokens = SerializingDependencyNodeVisitor.STANDARD_TOKENS;
        }

        return graphTokens;
    }

    private DependencyNodeFilter createDependencyNodeFilter() {
        List<DependencyNodeFilter> filters = new ArrayList<>();

        // filter includes
        if (includes != null) {
            List<String> patterns = Arrays.asList(includes.split(","));

            getLog().debug("+ Filtering dependency tree by artifact include patterns: " + patterns);

            ArtifactFilter artifactFilter = new StrictPatternIncludesArtifactFilter(patterns);
            filters.add(new ArtifactDependencyNodeFilter(artifactFilter));
        }

        // filter excludes
        if (excludes != null) {
            List<String> patterns = Arrays.asList(excludes.split(","));

            getLog().debug("+ Filtering dependency tree by artifact exclude patterns: " + patterns);

            ArtifactFilter artifactFilter = new StrictPatternExcludesArtifactFilter(patterns);
            filters.add(new ArtifactDependencyNodeFilter(artifactFilter));
        }

        return filters.isEmpty() ? null : new AndDependencyNodeFilter(filters);
    }

    /**
     * Serializes the specified dependency tree to a string.
     *
     * @param theRootNode the dependency tree root node to serialize
     * @return the serialized dependency tree
     */
    private String serializeDependencyTree(DependencyNode theRootNode) {
        StringWriter writer = new StringWriter();

        DependencyNodeVisitor visitor = getSerializingDependencyNodeVisitor(writer);

        // TODO: remove the need for this when the serializer can calculate last nodes from visitor calls only
        visitor = new BuildingDependencyNodeVisitor(visitor);

        DependencyNodeFilter filter = createDependencyNodeFilter();

        if (filter != null) {
            CollectingDependencyNodeVisitor collectingVisitor = new CollectingDependencyNodeVisitor();
            DependencyNodeVisitor firstPassVisitor = new FilteringDependencyNodeVisitor(collectingVisitor, filter);
            theRootNode.accept(firstPassVisitor);

            DependencyNodeFilter secondPassFilter =
                    new AncestorOrSelfDependencyNodeFilter(collectingVisitor.getNodes());
            visitor = new FilteringDependencyNodeVisitor(visitor, secondPassFilter);
        }

        theRootNode.accept(visitor);

        return writer.toString();
    }

}
