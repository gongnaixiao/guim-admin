package com.sinoiift.repository;

import com.sinoiift.domain.Pkg;
import com.sinoiift.domain.view.PkgInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xg on 2018/1/3.
 */
public interface PkgRepository extends JpaRepository<Pkg, Long>, JpaSpecificationExecutor{
    Pkg findByName(String name);
}
