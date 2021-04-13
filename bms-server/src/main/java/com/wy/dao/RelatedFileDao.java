package com.wy.dao;

import org.nutz.dao.Cnd;
import org.springframework.stereotype.Repository;

import com.wy.entity.RelatedFile;

@Repository
public class RelatedFileDao extends BaseDao<RelatedFile> {

	public boolean delByLocalName(String localName) {
		return dao.clear(RelatedFile.class, Cnd.where("local_name", "=", localName)) > 0;
	}
}