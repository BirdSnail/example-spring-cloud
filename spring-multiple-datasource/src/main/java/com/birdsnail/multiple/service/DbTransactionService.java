package com.birdsnail.multiple.service;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试多数据源下的数据库事务
 */
@Service
public class DbTransactionService {

    @Autowired
    private DbService dbService;

    /**
     * 不同数据源分别执行事务操作。必须使用{@link DSTransactional}注解
     */
    @DSTransactional
    public void testDsTransaction() {
        dbService.updateLocalData(false, 2);
        dbService.updateDevData(true, 112L);
    }

    /**
     * 同样的不同数据源分别执行事务操作，但是加了{@link Transactional}注解，会导致动态数据源切换失效
     */
    @Transactional
    public void testTransactionWithAnnotation() {
        dbService.updateLocalData(false, 2);
        dbService.updateDevData(false, 112L);
    }

    /**
     * 嵌套事务变更事务传播模式，产生新事物，保证使用切换后的dataSource
     */
    @Transactional
    public void testTransactionWithPropagationNew() {
        dbService.updateLocalDataNewTransaction(false, 2);
        dbService.updateDevDataNewTransaction(true, 112L);
    }

    /**
     * seata AT模式下的分布式事务
     */
    @GlobalTransactional
    public void testTransactionBySeataAt(Integer id, Long labelId) {
        dbService.updateLocalData(false, id);
        // 抛出异常，localData会被回滚
        dbService.updateDevData(true, labelId);
    }


}
