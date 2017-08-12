package com.samayu.prodcast.prodcastd;

import com.samayu.prodcast.prodcastd.dto.Employee;

/**
 * Created by sarathan732 on 8/11/2017.
 */

public class SessionInfo {

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private SessionInfo sessionInfo = new SessionInfo();

    public SessionInfo instance(){
        return sessionInfo;
    }

    public void destroy(){
        sessionInfo = new SessionInfo();
    }


}
