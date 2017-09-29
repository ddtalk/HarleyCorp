package com.alibaba.dingtalk.openapi.springbootdemo.dal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

@Table(name = "lufs_user")
public class LufsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "user_name")
    private String userName;

    private String password;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_gender")
    private String userGender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

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
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return user_address
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * @return user_gender
     */
    public String getUserGender() {
        return userGender;
    }

    /**
     * @param userGender
     */
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    /**
     * @return date_of_birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return created_by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return create_timestamp
     */
    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * @param createTimestamp
     */
    public void setCreateTimestamp(LocalDateTime createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * @return updated_by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return update_timestamp
     */
    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     * @param updateTimestamp
     */
    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}