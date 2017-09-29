package com.alibaba.dingtalk.openapi.springbootdemo.dal.model;

import javax.persistence.*;

@Table(name = "lufs_dtalk_xref")
public class LufsDtalkXref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "dtalk_userid")
    private String dtalkUserid;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return emp_no
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    /**
     * @return dtalk_userid
     */
    public String getDtalkUserid() {
        return dtalkUserid;
    }

    /**
     * @param dtalkUserid
     */
    public void setDtalkUserid(String dtalkUserid) {
        this.dtalkUserid = dtalkUserid;
    }
}