package com.sinoiift.repository;

import com.sinoiift.domain.DeployPlan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xg on 2018/1/3.
 */
public interface DeployPlanRepository extends JpaRepository<DeployPlan, Long> {
    int countByEnvIdAndPkgId(long envId, long pkgId);
}
