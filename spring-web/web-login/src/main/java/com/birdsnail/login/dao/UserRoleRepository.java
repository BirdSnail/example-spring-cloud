package com.birdsnail.login.dao;

import com.birdsnail.login.dao.entity.RoleEntity;
import com.birdsnail.login.dao.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query(value = """
            select r from UserEntity u 
                join UserRoleEntity ur on u.userId = ur.userId 
                    join RoleEntity r on ur.roleId = r.id
            where u.userId = :userId
            """)
    List<RoleEntity> findAllByUserId(@Param("userId") Long userId);


}
