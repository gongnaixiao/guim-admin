package com.sinoiift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xg on 2018/1/3.
 */
@Entity
public class Env extends Entitys{
    @Id
    @GeneratedValue
    @Getter@Setter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter@Setter
    private String name;
}
