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
 * ?????????????????????
 *
 * @author chenhx
 * @version UnittestPlugin.java, v 0.1 2019-05-30 17:47 chenhx
 */
@Mojo(name = "test")
public class UnittestPlugin extends AbstractPlugin {

    private Log log = new MySystemStreamLog();
    /**
     * ???????????????????????????
     */
    @Parameter
    private String testPackageName;
    /**
     * ??????
     */
    @Parameter(defaultValue = "")
    private String author;

    /**
     * ???????????????????????????
     */
    @Parameter(defaultValue = "true")
    private Boolean isGetChildPackage;

    /**
     * ????????????mock????????????????????????????????????????????????(??????true)
     */
    @Parameter(defaultValue = "true")
    private Boolean isMockThisOtherMethod;

    /**
     * ????????????????????????????????????????????????(??????false)
     */
    @Parameter(defaultValue = "false")
    private Boolean isSetBasicTypesRandomValue;

    /**
     * ?????????????????????????????????????????????"10"?????????10???????????????/???????????????
     * V1.1.2+
     */
    @Parameter(defaultValue = "10")
    private String setStringRandomRange;
    /**
     * ??????int/Integer????????????????????????????????????"0,1000"?????????[0,1000)?????????int???????????????????????????????????????"0",???int????????????0???
     * V1.1.2+
     */
    @Parameter(defaultValue = "0,1000")
    private String setIntRandomRange;
    /**
     * ??????long/Long????????????????????????(???????????????setIntRandomRange??????)
     * V1.1.2+
     */
    @Parameter(defaultValue = "0,10000")
    private String setLongRandomRange;
    /**
     * ??????boolean/Boolean?????????????????????????????????????????????"true"/"false"?????????????????????????????????????????????true???false?????????
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
            //???????????????
            super.execute();

            //????????????????????????
            init();

            log.info("???????????????????????????Case+Mock???????????????????????????");
            log.debug("??????????????????????????????????????????????????????" + "\n???????????????????????????" + CommonConstant.CONFIG_ENTITY);

            //????????????
            if (!paramCheck()) {
                return;
            }


            //?????????????????????????????????,key-?????????value-??????????????????
            Map<String, List<String>> javaListMap = new HashMap<>();
            //??????????????????java????????????????????????
            testPackageName = testPackageName.trim();
            String typeName = null; // class????????????
            if (testPackageName.endsWith(".java")) {
                //?????????????????????????????????????????????????????????????????????????????????????????????
                List<String> javaList = new ArrayList<>();
                typeName = testPackageName;
                typeName = typeName.substring(0, typeName.lastIndexOf("."));
                typeName = typeName.substring(typeName.lastIndexOf(".") + 1, typeName.length());

                testPackageName = testPackageName.substring(0, testPackageName.lastIndexOf(".java"));
                String classPathName = basedir + "/src/main/java/" + testPackageName.replace(".", "/") + ".java";
                log.info("????????????????????????????????????????????????"+classPathName);
                javaList.add(classPathName);
                javaListMap.put(testPackageName.substring(0, testPackageName.lastIndexOf(".")), javaList);
            } else {

                //?????????????????????????????????
                if (testPackageName.contains(";")) {
                    String[] packageNames = testPackageName.split(";");
                    for (String packageName : packageNames) {
                        if (StringUtils.isEmpty(packageName)) {
                            continue;
                        }
                        //?????????????????????
                        packageName = packageName.replaceAll("\\r|\\n", "").trim();
                        //???????????????????????????
                        List<String> javaList = PackageUtils.getClassName(basedir.getPath(), packageName, isGetChildPackage);
                        javaListMap.put(packageName, javaList);
                    }

                } else {
                    //???????????????????????????
                    List<String> javaList = PackageUtils.getClassName(basedir.getPath(), testPackageName, isGetChildPackage);
                    javaListMap.put(testPackageName, javaList);
                }

            }

            int count = 0;
            //??????javaListMap??????????????????????????????
            for (String packageName : javaListMap.keySet()) {
                //??????????????????????????????????????????
                List<String> javaList = javaListMap.get(packageName);
                log.info("???????????????????????????" + packageName );
                //class??????????????????
                for (String javaNamePath : javaList) {
                    count ++;
                    log.info("??????java???????????????????????????" + javaNamePath);
                    if (javaNamePath.endsWith("$1")) {
                        //??????
                        log.info("????????????????????????:" + javaNamePath);
                        continue;
                    }
                    //???????????????
                    JavaClassInfo javaClassInfo = new JavaClassInfo();

                    javaClassInfo.setPackageName(packageName);
    //                if(StringUtil.isNotEmpty( CommonConstant.CONFIG_ENTITY.getTestClassPackageName() )){
    //                    javaClassInfo.setPackageName(CommonConstant.CONFIG_ENTITY.getTestClassPackageName());
    //                }

                    javaClassInfo.setAbsolutePath(javaNamePath);
                    javaClassInfo.setFullyTypeName(javaClassInfo.getPackageName() + "." + javaClassInfo.getTypeName());

                    //???????????????????????????   ???????????????????????????
                    String testJavaName = CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + javaClassInfo.getTypeName() + CommonConstant.TEST_CLASS_SUFFIX + ".java";
                    javaClassInfo.setTestAbsolutePath(testJavaName);
                    javaClassInfo.setMockAbsolutePath(CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + javaClassInfo.getTypeName()+CommonConstant.MOCK_CLASS_SUFFIX + ".java");

                    if (null != typeName) {
                        javaClassInfo.setFullyTypeName(javaClassInfo.getPackageName() + "." + typeName);
                        javaClassInfo.setTestAbsolutePath(CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + typeName+CommonConstant.TEST_CLASS_SUFFIX + ".java");
                        javaClassInfo.setMockAbsolutePath(CommonConstant.CONFIG_ENTITY.getBasedir() + CommonConstant.JAVA_TEST_SRC + javaClassInfo.getPackageName().replace(".", "/") + "/" + typeName+CommonConstant.MOCK_CLASS_SUFFIX + ".java");
                        javaClassInfo.setTypeName(typeName);
                    }
                    log.debug("??????????????????javaClassInfo???" + JSON.toJSONString(javaClassInfo));

                    //??????
                    GenJava.genTest(javaClassInfo);
                }
            }

            if (count > 1) {
                log.info("??????????????????Case+Mock?????????????????????"+ ((System.currentTimeMillis()-startTime)/1000D) +"???");
            }
        } finally {
            //?????????????????????
            DownFile.removeTemplateFile();
        }
    }

    /**
     * ???????????????
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

        log.debug("init params???"+ JSON.toJSONString(CommonConstant.CONFIG_ENTITY));

        //?????????json??????
        DownFile.downJsonFile();

        //??????????????????
        DownFile.downTemplateFile();

        //???????????????,???????????????
        initJavaProjectBuilder();
    }

    /**
     * ????????????
     *
     * @return ???????????????true-???????????????false-????????????
     */
    private boolean paramCheck() {
        if (StringUtils.isEmpty(testPackageName)) {
            getLog().error("testPackageName???????????????????????????????????????????????????");
            return false;
        }
        return true;
    }

    /**
     * ???????????????
     */
    private void initJavaProjectBuilder() {
        try {
            String mainJava = basedir.getPath() + CommonConstant.JAVA_MAIN_SRC;
            log.info("???????????????????????????" + mainJava);
            CommonConstant.javaProjectBuilder.addSourceTree(new File(mainJava));
        } catch (Exception e) {
            log.error("?????????????????????java????????? ??????", e);
        }
        try {
            String testJava = basedir.getPath() + CommonConstant.JAVA_TEST_SRC;
            log.info("?????????????????????????????????" + testJava);
            CommonConstant.javaProjectBuilder.addSourceTree(new File(testJava));
        } catch (Exception e) {
            log.error("??????????????????????????????java????????? ??????", e);
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
            log.error("???????????????", e);
        }
    }

    private URL[]   getPackageUrls() throws MojoExecutionException {
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
