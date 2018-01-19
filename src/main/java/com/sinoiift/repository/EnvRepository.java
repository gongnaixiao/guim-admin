package com.sinoiift.repository;

import com.sinoiift.domain.Env;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xg on 2018/1/3.
 */
public interface EnvRepository extends JpaRepository<Env, Long> {
    Env findByName(String name);
}
