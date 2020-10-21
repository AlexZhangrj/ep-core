package com.zhrenjie04.alex.manager.service;

import com.zhrenjie04.alex.core.GenericService;
import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.domain.ProjectDept;
import com.zhrenjie04.alex.manager.dao.ProjectDeptDao;
/**
 * @author 张人杰
 */
public interface ProjectDeptService extends GenericService<ProjectDept,ProjectDeptDao>{
	JsonResult trees(User user);
}
