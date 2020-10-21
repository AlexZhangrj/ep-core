package com.zhrenjie04.alex.user.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.ProjectDept;
/**
 * @author 张人杰
 */
@Repository("projectDeptDao")
public interface ProjectDeptDao extends GenericDao<ProjectDept>{
	List<ProjectDept>treesComAndSubCom(HashMap<String,Object>params);
	List<ProjectDept>treesCom(HashMap<String,Object>params);
	List<ProjectDept>treesMy(HashMap<String,Object>params);
	List<ProjectDept>treesAll(HashMap<String,Object>params);
}
