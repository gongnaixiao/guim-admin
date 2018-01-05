package com.sinoiift.domain;

import lombok.Data;
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
public class SysEnv extends Entitys{
    @Id
    @GeneratedValue
    @Getter@Setter
    private Long id;
    @Column(nullable = false, unique = true)
    @Getter@Setter
    private String name;
    @Column
    @Getter@Setter
    private String appIp;
    @Column
    @Getter@Setter
    private String appUserName;
    @Column
    @Getter@Setter
    private String appPassWord;

    @Column
    @Getter@Setter
    private String dbIp;
    @Column
    @Getter@Setter
    private String dbUserName;
    @Column
    @Getter@Setter
    private String dbPassWord;
}
