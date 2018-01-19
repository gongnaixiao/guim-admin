package com.sinoiift.domain.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xg on 2018/1/16.
 */
public class Deploy implements Serializable {
    @Getter
    @Setter
    private Long pkgId;

    @Getter
    @Setter
    private Long envId;
}
