package com.birdsnail.login.dao;

import com.birdsnail.login.dao.entity.ResourceEntity;
import com.birdsnail.login.dao.entity.RoleResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleResourceRepository extends JpaRepository<RoleResourceEntity, Long> {


    /**
     * 查询角色拥有的资源权限
     *
     * @param roleIds 角色id
     * @return 资源实体对象集合
     */
    @Query("""
            select rs from RoleEntity r 
                join RoleResourceEntity rr on r.id = rr.roleId
                    join ResourceEntity rs on rr.resourceId = rs.id
            where r.id in (:roleIds)
            """)
    List<ResourceEntity> findAllByRoleId(@Param("roleIds") List<Long> roleIds);
}
