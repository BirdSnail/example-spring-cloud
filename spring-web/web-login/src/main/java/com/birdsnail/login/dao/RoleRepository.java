package com.birdsnail.login.dao;

import com.birdsnail.login.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {


}
