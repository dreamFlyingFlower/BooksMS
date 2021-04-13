package com.wy.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;
import com.wy.annotation.CheckAdd;
import com.wy.annotation.CheckUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("ti_role")
public class Role extends BaseBean<Role> {
    @Id
    @Column(hump=true)
    @CheckUpdate
    private Integer roleId;

    @Column(hump=true)
    @CheckAdd
    private String roleName;

    //角色状态,0不可见,只有超级管理员是这个状态,1正常
    @Column(hump=true)
    @Readonly
    private Byte roleState=1;
    
    // 多角色时,登录以等级高的为默认,最高为10000
    @Column(hump=true)
    private Integer roleLevel;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("roleId", roleId)
                .add("roleName", roleName)
                .add("roleState", roleState)
                .add("roleLevel", roleLevel)
                .toString();
    }
}