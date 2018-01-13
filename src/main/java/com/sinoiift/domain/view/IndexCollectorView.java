package com.sinoiift.domain.view;

import com.sinoiift.domain.User;

public class IndexCollectorView {
    //近一个月最活跃用户
    private User mostActiveUser;

    public User getMostActiveUser() {
        return mostActiveUser;
    }

    public void setMostActiveUser(User mostActiveUser) {
        this.mostActiveUser = mostActiveUser;
    }
}
