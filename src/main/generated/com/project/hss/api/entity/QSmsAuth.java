package com.project.hss.api.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSmsAuth is a Querydsl query type for SmsAuth
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSmsAuth extends EntityPathBase<SmsAuth> {

    private static final long serialVersionUID = 1745546546L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSmsAuth smsAuth = new QSmsAuth("smsAuth");

    public final EnumPath<com.project.hss.api.enums.SmsAuthUsage> authUsage = createEnum("authUsage", com.project.hss.api.enums.SmsAuthUsage.class);

    public final StringPath code = createString("code");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QMembers members;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final DateTimePath<java.time.LocalDateTime> sendExpire = createDateTime("sendExpire", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> validTime = createDateTime("validTime", java.time.LocalDateTime.class);

    public final StringPath verifyCode = createString("verifyCode");

    public final DateTimePath<java.time.LocalDateTime> verifyExpire = createDateTime("verifyExpire", java.time.LocalDateTime.class);

    public QSmsAuth(String variable) {
        this(SmsAuth.class, forVariable(variable), INITS);
    }

    public QSmsAuth(Path<? extends SmsAuth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSmsAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSmsAuth(PathMetadata metadata, PathInits inits) {
        this(SmsAuth.class, metadata, inits);
    }

    public QSmsAuth(Class<? extends SmsAuth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
    }

}

