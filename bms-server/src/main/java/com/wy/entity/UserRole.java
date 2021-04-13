package com.wy.entity;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("tr_user_role")
@PK({"userId","roleId"})
public class UserRole implements Serializable {
    @Column(hump=true)
    private Integer userId;

    @Column(hump=true)
    private Integer roleId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("userId", userId)
                .add("roleId", roleId)
                .toString();
    }
}