package com.aswe.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "INSERT_DT")
    @JsonIgnore
    private LocalDateTime insertDt;

    @CreatedBy
    @Column(name = "INSERT_USER_CD")
    @JsonIgnore
    private String insertUserCd;

    @LastModifiedDate
    @Column(name = "UPDATE_DT")
    @JsonIgnore
    private LocalDateTime updateDt;

    @LastModifiedBy
    @Column(name = "UPDATE_USER_CD")
    @JsonIgnore
    private String updateUserCd;

    @Column(name = "DELETE_YN")
    @JsonIgnore
    @Builder.Default
    private String deleteYn = "N";

    @Column(name = "DELETE_DT")
    @JsonIgnore
    private LocalDateTime deleteDt;

    @Column(name = "DELETE_USER_CD")
    @JsonIgnore
    private String deleteUserCd;

    @Column(name = "DELETE_REMARK")
    @JsonIgnore
    private String deleteRemark;
}