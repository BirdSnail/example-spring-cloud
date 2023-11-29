package com.birdsnail.multiple.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.birdsnail.multiple.base.City;
import com.birdsnail.multiple.base.CityMapper;
import com.birdsnail.multiple.base.EcLabel;
import com.birdsnail.multiple.base.EcLabelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DbService {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private EcLabelMapper ecLabelMapper;


    @DS("local")
    public City testDynamicLocalQuery(Integer id) {
        LambdaQueryWrapper<City> query = Wrappers.lambdaQuery(City.class).eq(City::getId, id);
        City city = cityMapper.selectOne(query);
        System.out.println(city);
        return city;
    }

    @DS("dev")
    public EcLabel testDynamicDevQuery(Long labelId) {
        LambdaQueryWrapper<EcLabel> labelQuery = Wrappers.lambdaQuery(EcLabel.class).eq(EcLabel::getLabelId, labelId);
        EcLabel ecLabel = ecLabelMapper.selectOne(labelQuery);
        System.out.println(ecLabel);
        return ecLabel;
    }

    @DS("local")
    @Transactional
    public void updateLocalData(boolean isError, Integer id) {
        City city = new City();
        city.setId(id);
        city.setName("yhd-datasource" + RandomUtils.nextInt(1, 100));
        cityMapper.updateById(city);
        if (isError) {
            int a = 1 / 0;
        }
    }

    @DS("dev")
    @Transactional
    public void updateDevData(boolean isError, Long labelId) {
        EcLabel ecLabel = new EcLabel();
        ecLabel.setLastUpdateDate(LocalDateTime.now());
        ecLabelMapper.update(ecLabel, Wrappers.lambdaUpdate(EcLabel.class).eq(EcLabel::getLabelId, labelId));
        if (isError) {
            int a = 1 / 0;
        }
    }

    @DS("local")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLocalDataNewTransaction(boolean isError, Integer id) {
        updateLocalData(isError, id);
    }

    /**
     * 开启新事务
     */
    @DS("dev")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateDevDataNewTransaction(boolean isError, Long labelId) {
        updateDevData(isError, labelId);
    }

}
