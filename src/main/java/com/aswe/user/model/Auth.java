package com.aswe.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_AUTH")
public class Auth extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "AUTH_CD")
    private String authCd;

    @Column(name = "AUTH_TYPE")
    private String authType;

    @JoinColumn(name = "USER_CD") @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    private User userEntity;

    public void setUser(User userEntity) {
        this.userEntity = userEntity;
    }
}