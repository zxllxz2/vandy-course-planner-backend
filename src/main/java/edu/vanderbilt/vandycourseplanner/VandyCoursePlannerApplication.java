package edu.vanderbilt.vandycourseplanner;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMPP
@MapperScan("edu.vanderbilt.vandycourseplanner.mapper")
public class VandyCoursePlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VandyCoursePlannerApplication.class, args);
    }

}
