package com.birdsnail.multiple;

import com.birdsnail.multiple.service.DbService;
import com.birdsnail.multiple.service.DbTransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootApplicationStart {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(SpringBootApplicationStart.class);
        DbService dbService = application.getBean(DbService.class);
//        dbService.testDynamicLocalQuery(2);
//        dbService.testDynamicDevQuery(112L);
//        dbService.updateLocalData(false, 2);

        DbTransactionService transactionService = application.getBean(DbTransactionService.class);
//        transactionService.testDsTransaction();
//        transactionService.testTransactionWithAnnotation();
//        transactionService.testTransactionWithPropagationNew();
    }

}
