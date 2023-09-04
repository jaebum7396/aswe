package com.aswe.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1475715186L;

    public static final QUser user = new QUser("user");

    public final com.aswe.common.model.QBaseEntity _super = new com.aswe.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDt = _super.deleteDt;

    //inherited
    public final StringPath deleteRemark = _super.deleteRemark;

    //inherited
    public final NumberPath<Long> deleteUserCd = _super.deleteUserCd;

    //inherited
    public final StringPath deleteYn = _super.deleteYn;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDt = _super.insertDt;

    //inherited
    public final NumberPath<Long> insertUserCd = _super.insertUserCd;

    public final SetPath<Auth, QAuth> roles = this.<Auth, QAuth>createSet("roles", Auth.class, QAuth.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    //inherited
    public final NumberPath<Long> updateUserCd = _super.updateUserCd;

    public final StringPath userCd = createString("userCd");

    public final StringPath userId = createString("userId");

    public final StringPath userPw = createString("userPw");

    public final StringPath userType = createString("userType");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

