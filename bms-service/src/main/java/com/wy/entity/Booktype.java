package com.wy.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("tb_booktype")
public class Booktype extends BaseBean<Booktype> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(hump = true)
	private Integer booktypeId;

	@Column(hump = true)
	private String booktypeName;

	@Column(hump = true)
	private String booketypeDesc;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues().add("booktypeId", booktypeId)
				.add("booktypeName", booktypeName).add("booketypeDesc", booketypeDesc).toString();
	}
}