package com.wy.entity;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("tr_role_button")
@PK({"roleId","buttonId"})
public class RoleButton implements Serializable {
    @Column(hump=true)
    private Integer roleId;

    @Column(hump=true)
    private Integer buttonId;

    private static final long serialVersionUID = 1L;
}