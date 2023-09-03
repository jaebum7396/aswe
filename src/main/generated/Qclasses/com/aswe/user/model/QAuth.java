package com.aswe.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = 1475121743L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuth auth = new QAuth("auth");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath authCd = createString("authCd");

    public final StringPath authType = createString("authType");

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

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    //inherited
    public final NumberPath<Long> updateUserCd = _super.updateUserCd;

    public final QUser userEntity;

    public QAuth(String variable) {
        this(Auth.class, forVariable(variable), INITS);
    }

    public QAuth(Path<? extends Auth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuth(PathMetadata metadata, PathInits inits) {
        this(Auth.class, metadata, inits);
    }

    public QAuth(Class<? extends Auth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userEntity = inits.isInitialized("userEntity") ? new QUser(forProperty("userEntity")) : null;
    }

}

