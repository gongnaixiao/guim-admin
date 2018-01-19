package com.sinoiift.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by xg on 2018/1/15.
 */
@AllArgsConstructor
@NoArgsConstructor
public class EnvApp implements Serializable {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String app;
    @Getter
    @Setter
    private String appIP;
    @Getter
    @Setter
    private String appUsername;
    @Getter
    @Setter
    private String appPassword;

    @Getter
    @Setter
    private String dbUrl;
    @Getter
    @Setter
    private String dbUsername;
    @Getter
    @Setter
    private String dbPassword;
    @Getter
    @Setter
    private String workspace;

    @Getter
    @Setter
    private String apWarDir;

    @Getter
    @Setter
    private String upWarDir;
}
