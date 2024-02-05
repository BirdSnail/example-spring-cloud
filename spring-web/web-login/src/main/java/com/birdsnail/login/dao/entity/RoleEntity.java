package com.birdsnail.login.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_role")
@Accessors(chain = true)
public class RoleEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 角色名称
     */
    private String role;

    /**
     * 角色描述
     */
    private String desc;

    private LocalDateTime createTime;

}
