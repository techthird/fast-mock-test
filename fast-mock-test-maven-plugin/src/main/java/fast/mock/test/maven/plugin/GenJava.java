/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.maven.plugin;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import fast.mock.test.core.build.impl.BuildClassImpl;
import fast.mock.test.core.constant.CommonConstant;
import fast.mock.test.core.dto.JavaClassDTO;
import fast.mock.test.core.info.JavaClassInfo;
import fast.mock.test.core.json.JsonConfig;
import fast.mock.test.core.util.StringUtils;
import fast.mock.test.core.util.UUIDUtils;
import fast.mock.test.core.log.MySystemStreamLog;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.maven.plugin.logging.Log;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * @author chenhx
 * @version GenJava.java, v 0.1 2019-06-24 16:47 chenhx
 */
public class GenJava {

    private static Log log = new MySystemStreamLog();

    public static void main(String[] args) {

        String json = "{\n" +
                "  \"isOpen\": false,\n" +
                "  \"list\":\n" +
                "  [\n" +
                "    {\n" +
                "      \"scope\":\"作用域：全局（global）、包（package）、类（class）、方法（method） - 默认全局\",\n" +
                "      \"scopeValue\": \"作用域的值，global则无需配置该值，package则为包名，class则为类名，method则为方法名\",\n" +
                "      \"type\": \"JavaBean类型（custom）/基础类型（base） - 默认自定义类型\",\n" +
                "      \"name\": \"类型的全限定名称\",\n" +
                "      \"value\":{\n" +
                "        \"若type=base，则该值固定为value\":\"值\",\n" +
                "        \"若type=custom，自定义类型，value下的json为fastjson序列化的json值\":\"值\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "\n";

        JsonConfig jsonConfig = JSON.parseObject(json, JsonConfig.class);
        System.out.println(jsonConfig.getList().get(0).getValue());

        System.out.println(jsonConfig);

    }

    /**
     * 开始生成测试类入口
     *
     * @param javaClassInfo 参数
     * @param startTime
     */
    public static void genTest(JavaClassInfo javaClassInfo) {
        long startTime = System.currentTimeMillis();
        try {
            //设置配置文件 freemarker
            Configuration cfg = getConfiguration();

            //已经生成的测试类的方法名称
            Set<String> testMethodNameSet = new HashSet<>();
            Set<String> mockMethodNameSet = new HashSet<>();

            //文件是否存在，false-不存在
            boolean fileIsExists = false;

            File testFile = new File(javaClassInfo.getTestAbsolutePath());
            File mockFile = new File(javaClassInfo.getMockAbsolutePath());
            //已经存在文件
            if (testFile.exists()) {
                log.info("已经存在，进行类方法追加生成！ "+ testFile +" "+ mockFile);
                //获取当前测试的类信息
                String testClassName = javaClassInfo.getPackageName() + "." + javaClassInfo.getTypeName() + CommonConstant.TEST_CLASS_SUFFIX;
                JavaClass testJavaClass = CommonConstant.javaProjectBuilder.getClassByName(testClassName);
                //遍历方法
                List<JavaMethod> javaMethodList = testJavaClass.getMethods();
                for (JavaMethod javaMethod : javaMethodList) {
                    testMethodNameSet.add(javaMethod.getName());
                    log.debug("获取测试类的方法名称:" + javaMethod.getName());
                }
                log.debug("获取的已经生成的类方法名称为:" + javaMethodList + ",测试类：" + testClassName);

                // 处理mock类

                String mockClassName = javaClassInfo.getPackageName() + "." + javaClassInfo.getTypeName() + CommonConstant.MOCK_CLASS_SUFFIX;
                JavaClass mockJavaClass = CommonConstant.javaProjectBuilder.getClassByName(mockClassName);
                //遍历方法
                for (JavaMethod javaMethod : mockJavaClass.getMethods()) {
                    mockMethodNameSet.add(javaMethod.getName());
                }

                fileIsExists = true;

            } else {
                if (!testFile.getParentFile().exists() && !testFile.getParentFile().mkdirs()) {
                    log.error(testFile.getParentFile() + "文件的路径不存在，本次生成失败");
                    return;
                }
            }

            //构建参数，核心
            JavaClassDTO javaClassDTO = BuildClassImpl.build(javaClassInfo);

            //获取类中方法
            if (javaClassDTO == null) {
                return;
            }

            Map<String, Object> data = new HashMap<>(2);
            data.put("javaClassDTO", javaClassDTO);
            log.info("javaClassDTO：");
            log.info(JSON.toJSONString(javaClassDTO));

            //获取mock的类
            if (!fileIsExists) {
                //文件不存在,进行初始化生成
                cfg.getTemplate(CommonConstant.CONFIG_ENTITY.getConfigFileName()).process(data, new FileWriter(testFile));
                cfg.getTemplate(CommonConstant.CONFIG_ENTITY.getMockConfigFileName()).process(data, new FileWriter(mockFile));
                log.info("生成成功！Test类：" + testFile);
                log.info("生成成功！Mock类：" + mockFile);
            } else {
                //文件已经存在，进行追加方法
                // 生成新的Case
                File newTestFile = fileIsExists(javaClassInfo.getTypeName(), cfg, testMethodNameSet, testFile,mockMethodNameSet, mockFile, javaClassDTO, data, javaClassInfo);
                //File newMockFile = appendMockFileIsExists(javaClassInfo.getTypeName(), cfg, mockMethodNameSet, mockFile, javaClassDTO, data, javaClassInfo);
                /*if (newFile == null) {
                    log.error(javaClassInfo.getFullyTypeName()+" 追加方法失败");
                    return;
                }*/
                //log.info(testFile + ", 追加方法成功，生成的临时文件:" + newFile);
            }

            log.info("成功生成测试Case+Mock代码！类名："+javaClassInfo.getTypeName()+" 耗时："+ ((System.currentTimeMillis()-startTime)/1000D) +"秒");
        } catch (Exception e) {
            log.error("生成测试Case+Mock代码失败！类名："+javaClassInfo.getTypeName()+" 耗时："+ ((System.currentTimeMillis()-startTime)/1000D) +"秒！异常：", e);
        }
    }

    /**
     * 文件存在时，进行追加生成的方法
     * 生成一个临时文件，将临时文件中多出的方法写入到原来的文件中
     * @param className         类名
     * @param configuration     Template模板生成文件
     * @param testMethodNameSet 已有测试方法的名称
     * @param testFile              原有已生成的文件
     * @param javaClassDTO      生成的临时文件信息
     * @param data              模板的数据
     * @param javaClassInfo     类通用信息
     * @return 临时生成的文件
     * @throws TemplateException 模板异常
     * @throws IOException       流异常
     */
    private static File fileIsExists(String className, Configuration configuration, Set<String> testMethodNameSet, File testFile,
                                    Set<String> mockMethodNameSet, File mockFile,JavaClassDTO javaClassDTO, Map<String, Object> data,
                                     JavaClassInfo javaClassInfo) throws TemplateException, IOException {
        //测试类已经存在了
        String randId = UUIDUtils.getID();
        String testJavaName = getTestJavaName(javaClassInfo, className, randId);

        javaClassDTO.setModelNameUpperCamelTestClass(className + CommonConstant.TEST_CLASS_SUFFIX + "_"+ randId);
        javaClassDTO.setModelNameUpperCamelMockClass(className + CommonConstant.MOCK_CLASS_SUFFIX + "_"+ randId);
        javaClassDTO.setModelNameLowerCamelTestClass(StringUtils.strConvertLowerCamel(className + CommonConstant.TEST_CLASS_SUFFIX + "_"+ randId));

        File newTestFile = new File(testJavaName);
        if (!newTestFile.getParentFile().exists() && !newTestFile.getParentFile().mkdirs()) {
            log.error(newTestFile.getParentFile() + "生成失败，请检查是否有权限");
            return null;
        }

        String mockJavaName = getMockJavaName(javaClassInfo, className, randId);
        File newMockFile = new File(mockJavaName);
        if (!newMockFile.getParentFile().exists() && !newMockFile.getParentFile().mkdirs()) {
            log.error(newMockFile.getParentFile() + "生成失败，请检查是否有权限");
            return null;
        }

        configuration.getTemplate(CommonConstant.CONFIG_ENTITY.getConfigFileName()).process(data, new FileWriter(newTestFile));
        configuration.getTemplate(CommonConstant.CONFIG_ENTITY.getMockConfigFileName()).process(data, new FileWriter(newMockFile));

        //读取包下所有的测试的java类文件
        CommonConstant.javaProjectBuilder.addSourceTree(newTestFile);
        CommonConstant.javaProjectBuilder.addSourceTree(newMockFile);

        //读取类的方法
        String newTestClassName = javaClassInfo.getPackageName() + "." + className + CommonConstant.TEST_CLASS_SUFFIX + "_"+ randId;
        String newMockClassName = javaClassInfo.getPackageName() + "." + className + CommonConstant.MOCK_CLASS_SUFFIX + "_"+ randId;
        appendFile(newTestClassName, testMethodNameSet, testFile , newTestFile, true);
        appendFile(newMockClassName, mockMethodNameSet, mockFile, newMockFile, false);

        return newTestFile;
    }

    private static void appendFile(String newClassName, Set<String> testMethodNameSet, File file, File newFile,boolean isTest) throws TemplateException, IOException {

        log.debug("获取临时生成的类名:" + newClassName);
        JavaClass testJavaClass = CommonConstant.javaProjectBuilder.getClassByName(newClassName);
        List<JavaMethod> javaMethodList = testJavaClass.getMethods();
        log.debug("获取的方法名称:" + javaMethodList);
        Set<String> appendSet = new HashSet<>();
        for (JavaMethod javaMethod : javaMethodList) {
            if (!testMethodNameSet.contains(javaMethod.getName())) {
                appendSet.add(javaMethod.getName());

                //新增的方法 - 测试方法的源码
                String code = javaMethod.getSourceCode();
                log.debug("获取追加的方法源码为:" + code);
                //原来的文件进行追加方法
                String methodStr;
                if (isTest){
                    methodStr = "\n    @Test\n" +
                            "    public void " + javaMethod.getName() + "(){\n" +
                            code + "\n" +
                            "    }\n}";
                } else {
                    StringBuilder str = new StringBuilder();
                    String codeBlock = javaMethod.getCodeBlock();
                    str.append("\r\n");
                    str.append("\t/**\n\t * "+javaMethod.getComment() +"\n\t */\r\n");
                    str.append("\t");
                    str.append(codeBlock.substring(codeBlock.indexOf("@"),codeBlock.indexOf("private")).replaceAll("\n|\t|com.alibaba.testable.core.annotation.",""));
                    str.append("\r\n");
                    str.append("\tprivate "+ javaMethod.getReturns().getGenericValue()+" "+javaMethod.getName()+"(");
                    if (!CollectionUtils.isEmpty(javaMethod.getParameters())) {
                        List<JavaParameter> parameters = javaMethod.getParameters();
                        for (int i = 0; i < parameters.size(); i++) {
                            JavaParameter parameter = parameters.get(i);
                            str.append(i > 0 ? "," : "");
                            str.append(parameter.getValue()+" "+parameter.getName());
                        }
                    }
                    str.append("){");
                    str.append(code);
                    str.append("}\r\n}");

                    methodStr = str.toString();
                }


                //文件追加
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String lineContent = null;
                List<String> oldFileStr = new ArrayList<>();
                while ((lineContent = br.readLine()) != null) {
                    oldFileStr.add(lineContent);
                }
                br.close();
                fileReader.close();
                //文件覆盖
                for (int i = oldFileStr.size() - 1; i >= 0; i--) {
                    String line = oldFileStr.get(i).trim();
                    if (StringUtils.isEmpty(line)) {
                        continue;
                    }
                    if ("}".equals(line)) {
                        //删除该行
                        oldFileStr.remove(i);
                        break;
                    }
                    if (line.endsWith("}")) {
                        //删除该行最后一个}字符
                        oldFileStr.set(i, oldFileStr.get(i).substring(0, oldFileStr.get(i).lastIndexOf("}")));
                        break;
                    }
                }

                StringBuilder fileStr = new StringBuilder();
                //覆盖保存文件
                for (String line : oldFileStr) {
                    fileStr.append(line).append("\n");
                }
                fileStr.append(methodStr);
                //写入文件
                PrintStream ps = new PrintStream(new FileOutputStream(file));
                // 往文件里写入字符串
                ps.println(fileStr);
                ps.close();
            }
            log.debug("获取临时测试类的方法名称:" + javaMethod.getName());
        }
        if (!appendSet.isEmpty()) {
            log.info((isTest?"Test":"Mock")+"类追加方法成功：" + JSON.toJSONString(appendSet));
        }
        //删除文件
        if (!newFile.delete()) {
            for (int i = 0; i < 10; i++) {
                System.gc(); // 强制删除
                if (newFile.delete()) {
                    break;
                }
            }
            if (!newFile.delete()) {
                log.debug("删除临时文件失败，请手动删除，若已删除请忽略！！！文件:" + newFile);
            }
        }
    }

    private static String getTestJavaName(JavaClassInfo javaClassInfo, String className, String randId) {
        return CommonConstant.CONFIG_ENTITY.getBasedir() +
                CommonConstant.JAVA_TEST_SRC +
                javaClassInfo.getPackageName().replace(".", "/") + "/" + className + CommonConstant.TEST_CLASS_SUFFIX +"_"+ randId + ".java";
    }
    private static String getMockJavaName(JavaClassInfo javaClassInfo, String className, String randId) {
        return CommonConstant.CONFIG_ENTITY.getBasedir() +
                CommonConstant.JAVA_TEST_SRC +
                javaClassInfo.getPackageName().replace(".", "/") + "/" + className + CommonConstant.MOCK_CLASS_SUFFIX +"_"+ randId + ".java";
    }

    /**
     * 生成freemarker引擎配置
     *
     * @return
     * @throws IOException
     */
    private static Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        //模板文件
        File file = new File(CommonConstant.CONFIG_ENTITY.getBasedir().getPath() + CommonConstant.CONFIG_ENTITY.getConfigPath());
        cfg.setDirectoryForTemplateLoading(file);
        //设置文件编码
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

}
