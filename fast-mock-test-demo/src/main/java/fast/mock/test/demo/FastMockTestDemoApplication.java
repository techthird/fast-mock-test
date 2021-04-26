package fast.mock.test.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("fast.mock.test.demo.mapper")
public class FastMockTestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastMockTestDemoApplication.class, args);
    }
}
