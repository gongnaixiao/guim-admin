package com.sinoiift.repository;

import com.sinoiift.domain.Env;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by xg on 2018/1/3.
 */
public interface EnvRepository extends JpaRepository<Env, Long> {
    List<Env> findAll();
}
