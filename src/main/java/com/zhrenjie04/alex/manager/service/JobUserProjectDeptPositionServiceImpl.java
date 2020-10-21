package com.zhrenjie04.alex.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.manager.domain.JobUserProjectDeptPosition;
import com.zhrenjie04.alex.manager.dao.JobUserProjectDeptPositionDao;
/**
 * @author 张人杰
 */
@Service("userProjectDeptPositionService")
public class JobUserProjectDeptPositionServiceImpl extends AbstractGenericService<JobUserProjectDeptPosition,JobUserProjectDeptPositionDao> implements JobUserProjectDeptPositionService {

	@Autowired
	JobUserProjectDeptPositionDao userProjectDeptPositionDao;

	@Override
	protected JobUserProjectDeptPositionDao getDao() {
		return userProjectDeptPositionDao;
	}	
}
