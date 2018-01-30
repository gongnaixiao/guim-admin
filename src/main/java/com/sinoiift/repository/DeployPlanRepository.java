package com.sinoiift.repository;

import com.sinoiift.domain.DeployPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xg on 2018/1/3.
 */
public interface DeployPlanRepository extends JpaRepository<DeployPlan, Long> {
    int countByEnvIdAndPkgId(long envId, long pkgId);

    @Query("update DeployPlan set isPrd=true where envId=?1 and pkgId=?2")
    @Modifying
    void updateStatus(Long envId, Long pkgId);
}
