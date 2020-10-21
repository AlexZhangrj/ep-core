package com.zhrenjie04.alex.user.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.zhrenjie04.alex.core.User;

/**
 * @author 张人杰
 */
@Repository("jobDao")
public interface JobDao{
	/**
	 * 求总数
	 * @param params
	 * @return
	 */
	Long count(HashMap<String,Object>params);
	/**
	 * 分页查询数据
	 * @param params
	 * @return
	 */
	List<User> pageQueryAll(HashMap<String,Object>params);
	/**
	 * 虚拟删除
	 * @param params
	 * @return
	 */
	Long deleteJobUserRoles(HashMap<String,Object>params);
	/**
	 * 清理没有Roles配置的JobUserOrgPosition
	 * @return
	 */
	Long deleteCleanJobUserOrgPosition(HashMap<String,Object>params);
	/**
	 * 通过JobUserRoleIds查询userIds
	 * @param params，传入ids
	 * @return
	 */
	List<String> queryUserIdsByJobUserRoleIds(HashMap<String,Object>params);
}
