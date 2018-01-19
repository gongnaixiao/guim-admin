package com.sinoiift.repository;

import com.sinoiift.domain.view.PkgInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by xg on 2018/1/16.
 */

public interface CustomPkgRepository {
    Page<PkgInfo> listPkgInfos(Pageable pageable, String bug, String pkg, Long envId);
}
