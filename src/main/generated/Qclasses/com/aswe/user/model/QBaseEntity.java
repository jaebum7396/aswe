package com.aswe.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity> {

    private static final long serialVersionUID = 740495739L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final DateTimePath<java.time.LocalDateTime> deleteDt = createDateTime("deleteDt", java.time.LocalDateTime.class);

    public final StringPath deleteRemark = createString("deleteRemark");

    public final NumberPath<Long> deleteUserCd = createNumber("deleteUserCd", Long.class);

    public final StringPath deleteYn = createString("deleteYn");

    public final DateTimePath<java.time.LocalDateTime> insertDt = createDateTime("insertDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> insertUserCd = createNumber("insertUserCd", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> updateUserCd = createNumber("updateUserCd", Long.class);

    public QBaseEntity(String variable) {
        super(BaseEntity.class, forVariable(variable));
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEntity(PathMetadata metadata) {
        super(BaseEntity.class, metadata);
    }

}

