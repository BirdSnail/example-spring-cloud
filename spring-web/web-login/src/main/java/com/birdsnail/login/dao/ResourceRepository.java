package com.birdsnail.login.dao;

import com.birdsnail.login.dao.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

}
