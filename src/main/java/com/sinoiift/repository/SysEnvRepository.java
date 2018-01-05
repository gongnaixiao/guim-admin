package com.sinoiift.repository;

import com.sinoiift.domain.SysEnv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by xg on 2018/1/3.
 */
public interface SysEnvRepository extends JpaRepository<SysEnv, Long> {
    List<SysEnv> findAll();
}
