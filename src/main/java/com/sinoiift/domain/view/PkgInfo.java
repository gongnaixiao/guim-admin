package com.sinoiift.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xg on 2018/1/12.
 */
@AllArgsConstructor
@NoArgsConstructor
public class PkgInfo implements Serializable {
    @Getter
    @Setter
    private Long pkgId;
    @Getter
    @Setter
    private Long envId;
    @Getter
    @Setter
    private String envName;
    @Getter
    @Setter
    private boolean isPrd;
    @Getter
    @Setter
    private String bug;
    @Getter
    @Setter
    private String author;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Date ctime;
    @Getter
    @Setter
    private Long version;
}