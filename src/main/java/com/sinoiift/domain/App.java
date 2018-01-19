package com.sinoiift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xg on 2018/1/15.
 */
@Entity
public class App extends Entitys {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private Long envId;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String app;

    @Column(nullable = false)
    @Getter
    @Setter
    private String appIP;

    @Column(nullable = false)
    @Getter
    @Setter
    private String appUsername;

    @Column(nullable = false)
    @Getter
    @Setter
    private String appPassword;

    @Column(nullable = false)
    @Getter
    @Setter
    private String apWarDir;

    @Column(nullable = false)
    @Getter
    @Setter
    private String upWarDir;

    @Column(nullable = false)
    @Getter
    @Setter
    private String dbUrl;

    @Column(nullable = false)
    @Getter
    @Setter
    private String dbUsername;

    @Column(nullable = false)
    @Getter
    @Setter
    private String dbPassword;

    @Column(nullable = false)
    @Getter
    @Setter
    private String workspace;
}
