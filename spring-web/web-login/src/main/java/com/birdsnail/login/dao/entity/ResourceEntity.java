package com.birdsnail.login.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_resource")
@Accessors(chain = true)
public class ResourceEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String path;

    private LocalDateTime createTime;

}
