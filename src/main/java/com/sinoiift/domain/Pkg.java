package com.sinoiift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by xg on 2018/1/11.
 */

@Entity
public class Pkg extends Entitys {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String bug;

    @Column(nullable = false)
    @Getter
    @Setter
    private String author;

    @Column(nullable = false)
    @Getter
    @Setter
    private Long version;

    @Lob
    @Column(nullable = false)
    @Getter
    @Setter
    private byte[] content;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date ctime;
}
