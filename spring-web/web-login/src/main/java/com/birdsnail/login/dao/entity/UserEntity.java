package com.birdsnail.login.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "tb_user")
@EntityListeners(value = AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue
    private Long userId;

    private String username;

    private String password;

}
