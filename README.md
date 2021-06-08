fast-mock是一个快速生成测试类、Mock方法的框架，它使写集成、单元测试这件事情，变得简单而有趣！

一方面，写单元测试本身比较繁琐，技术挑战不大，很多程序员不愿意去写；  
另一方面，国内研发比较偏向“快、糙、猛” ，容易因为开发进度紧，导致单元测试的执行虎头蛇尾。  
关键问题还是研发团队需要建立对单元测试正确的认知，渐进明细，逐步改进。

fast-mock可以解决繁琐的单测编写，提升工作效率，让程序员更专注业务的实现。  
PS：团队前期没有单测的沉淀，可以先从整个接口的集成测试开始，逐步重构代码，往单测的方向靠近。

## 特点
* **快速生成测试类**： 生成一个包含设置Case名称、组装测试接口的参数、调用方法、断言的测试模板。
* **自动识别生成Mock方法**：根据测试接口自动识别需要Mock的方法，并且进行代码生成。
* **自动构造入参、出参实体**：根据入参、出参对象，自动进行数据构造。简单理解就是实例化对象，并进行set操作。


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
            <!-- 待测试类的包名com.yunji.demo.service.provider，或者测试指定类文件xxxx.java） -->
            <testPackageName>com.yunji.demo.service.provider.ItemReadServiceImpl.java</testPackageName>
            <!-- 指定生成Test方法，多个方法以,号分隔，默认生成一个类中所有方法 -->
            <testMethods>method1,method2</testMethods>
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
在引入插件的项目模块下运行maven插件 fast-mock:test 命令
```
mvn fast-mock:test
```   
![image](https://user-images.githubusercontent.com/20860404/120413358-b2f16600-c38a-11eb-95ae-691f7f1b8718.png)
![image](https://user-images.githubusercontent.com/20860404/120413784-825dfc00-c38b-11eb-96eb-20f0abda2d66.png)
![image](https://user-images.githubusercontent.com/20860404/121116700-88e7ea00-c849-11eb-908f-83dce6c263b1.png)


## 感谢







