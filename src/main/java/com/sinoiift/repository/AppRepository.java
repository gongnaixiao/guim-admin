package com.sinoiift.repository;

import com.sinoiift.domain.App;
import com.sinoiift.domain.Pkg;
import com.sinoiift.domain.view.EnvApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xg on 2018/1/15.
 */
public interface AppRepository extends JpaRepository<App, Long> {
    @Query("select new com.sinoiift.domain.view.EnvApp(a.id, e.name, a.app, a.appIP, a.appUsername, a.appPassword, a.dbUrl, a.dbUsername, a.dbPassword, a.workspace, a.apWarDir, a.upWarDir) from App a, Env e where a.envId = e.id")
    Page<EnvApp> findEnvs( Pageable pageable);

    @Query("select new com.sinoiift.domain.view.EnvApp(a.id, e.name, a.app, a.appIP, a.appUsername, a.appPassword, a.dbUrl, a.dbUsername, a.dbPassword, a.workspace, a.apWarDir, a.upWarDir) from App a, Env e where a.envId = e.id and e.name like ?1")
    Page<EnvApp> findEnvsLike(String name, Pageable pageable);

    Long countByEnvId(long envId);

    List<App> findByEnvId(long envId);
}
