# fast-mock-test
## 使用  
### 1、引入mock依赖：
```
<properties>
    <testable.version>0.6.2</testable.version>
</properties>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.alibaba.testable</groupId>
    <artifactId>testable-all</artifactId>
    <version>${testable.version}</version>
    <scope>test</scope>
</dependency>
```
### 2、依赖插件：
 ```
<plugins>
    <plugin>
        <groupId>fast-mock-test</groupId>
        <artifactId>fast-mock-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <configuration>
            <!-- 待测试类的包名，或者测试指定类文件xxxx.java） -->
            <testPackageName>com.yunji.demo.service.provider.ItemReadServiceImpl.java</testPackageName>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.2</version>
        <configuration>
            <argLine>-javaagent:${settings.localRepository}/com/alibaba/testable/testable-agent/${testable.version}/testable-agent-${testable.version}.jar</argLine>
        </configuration>
    </plugin>
</plugins>
```

### 3、生成测试代码 
在引入插件的项目模块下运行maven插件的 fast-mock:test 命令
```
mvn fast-mock:test
```   

直接运行mvn fast-test:test即可下载模板文件&生成测试类 
![image](https://user-images.githubusercontent.com/20860404/120413358-b2f16600-c38a-11eb-95ae-691f7f1b8718.png)
![image](https://user-images.githubusercontent.com/20860404/120413685-5c385c00-c38b-11eb-9881-1de046e1c778.png)





