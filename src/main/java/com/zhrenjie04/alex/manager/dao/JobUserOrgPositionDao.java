package com.zhrenjie04.alex.manager.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.JobUserOrgPosition;
/**
 * @author 张人杰
 */
@Repository("userOrgPositionDao")
public interface JobUserOrgPositionDao extends GenericDao<JobUserOrgPosition>{
	List<String> queryAllJobIds(HashMap<String,Object>params);
}
