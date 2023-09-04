package com.aswe.user.model;

import com.aswe.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@Entity(name = "TB_USER")
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "USER_CD")
    private String userCd;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    private Set<Auth> roles = new HashSet<>();

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "USER_PW", nullable = false)
    private String userPw;

    @Column(name = "USER_TYPE",nullable = true) @ColumnDefault("1")
    private String userType;

    public void setRoles(Set<Auth> roles) {
        this.roles = new HashSet<>(roles);
        //roles.forEach(o -> o.setUser(this));
    }
}