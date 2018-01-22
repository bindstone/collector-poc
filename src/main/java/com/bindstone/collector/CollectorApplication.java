package com.bindstone.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollectorApplication {
    //https://jira.atlassian.com/rest/api/2/mypermissions
    //https://www.programcreek.com/java-api-examples/index.php?source_dir=arquillian-governor-master/jira/

    public static void main(String[] args) {
        SpringApplication.run(CollectorApplication.class, args);
    }
}
