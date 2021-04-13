package com.wy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wy.dao.DicDao;
import com.wy.entity.Dic;

@Service("dicService")
public class DicService extends BaseService<Dic> {
    @Autowired
    private DicDao dicDao;

    @Override
    public DicDao getDao() {
        return dicDao;
    }
    
    public Map<String,Object> getPc(Integer dicId){
    	return dicDao.getPc(dicId);
    }
    
    public List<Map<String,Object>> getChildren(String dicCode) {
		return dicDao.getChildren(dicCode);
	}
    
    public List<Map<String, Object>> getTrees(Integer dicId){
    	return dicDao.getTrees(dicId);
    }
}