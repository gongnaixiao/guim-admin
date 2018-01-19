package com.sinoiift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xg on 2018/1/11.
 */
@Entity
public class DeployPlan extends Entitys {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private Long envId;

    @Column(nullable = false)
    @Getter
    @Setter
    private Long pkgId;

    @Column(nullable = false)
    @Getter
    @Setter
    private boolean isPrd;
}
