package org.yudi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.yudi.mapper")
public class QuickStart {
    public static void main( String[] args ) {
        SpringApplication.run(QuickStart.class, args);
    }
}
