# fast-mock-test
## 使用  
### 1、引入mock依赖：
```
<dependency>
    <groupId>org.powermock</groupId>
    <artifactId>powermock-module-junit4</artifactId>
    <version>1.7.4</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.powermock</groupId>
    <artifactId>powermock-api-mockito2</artifactId>
    <version>1.7.4</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>2.8.47</version>
    <scope>test</scope>
</dependency>
```
### 2、依赖插件：
 ```
<plugins>
    <plugin>
        <groupId>fast-mock-test</groupId>
        <artifactId>fast-test-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <configuration>
            <!-- 待测试类的包名，或者测试指定类文件xxxx.java） -->
            <testPackageName>fast.mock.test.demo.service.impl2</testPackageName>
        </configuration>
    </plugin>
</plugins>
```

### 4、生成测试代码 
在引入插件的项目模块下运行maven插件的 fast-test:test 命令    
```
mvn fast-test:test
```   

直接运行mvn fast-test:test即可下载模板文件&生成测试类 
![Uploading image.png…]()
![Uploading image.png…]()


