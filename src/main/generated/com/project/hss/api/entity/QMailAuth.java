package com.project.hss.api.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMailAuth is a Querydsl query type for MailAuth
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMailAuth extends EntityPathBase<MailAuth> {

    private static final long serialVersionUID = -1602829426L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMailAuth mailAuth = new QMailAuth("mailAuth");

    public final EnumPath<com.project.hss.api.enums.MailAuthUsage> authUsage = createEnum("authUsage", com.project.hss.api.enums.MailAuthUsage.class);

    public final StringPath code = createString("code");

    public final StringPath email = createString("email");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QMembers members;

    public final DateTimePath<java.time.LocalDateTime> sendExpire = createDateTime("sendExpire", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> validTime = createDateTime("validTime", java.time.LocalDateTime.class);

    public final StringPath verifyCode = createString("verifyCode");

    public final DateTimePath<java.time.LocalDateTime> verifyExpire = createDateTime("verifyExpire", java.time.LocalDateTime.class);

    public QMailAuth(String variable) {
        this(MailAuth.class, forVariable(variable), INITS);
    }

    public QMailAuth(Path<? extends MailAuth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMailAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMailAuth(PathMetadata metadata, PathInits inits) {
        this(MailAuth.class, metadata, inits);
    }

    public QMailAuth(Class<? extends MailAuth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
    }

}

