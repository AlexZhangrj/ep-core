package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.JobDao;
import com.zhrenjie04.alex.util.SessionUtil;

/**
 * @author 张人杰
 */
@Service("jobService")
public class JobServiceImpl implements JobService {

	protected static final String ROOT_GROUP_ID = "0";
	protected static final String ROOT_ORG_ID = "0";

	@Autowired
	JobDao jobDao;

	@Override
	public JsonResult pageQueryAll(HashMap<String, Object> params, User sessionUser) {
		if (sessionUser.getCurrentJob().getGroupId() == null) {
			return JsonResult.failure(JsonResult.CODE_PREREQUISITE_NOT_SATISFIED, "您的groupId为空，不能返回相应账号信息");
		}
		if (sessionUser.hasPrivilege("manager:user.back.page-query-all-groups")
				&& ROOT_GROUP_ID.equals(sessionUser.getGroupId())
				&& ROOT_GROUP_ID.equals(sessionUser.getCurrentJob().getGroupId())) {
			// 返回所有账号权限
		} else if (sessionUser.hasPrivilege("manager:user.back.page-query-all-coms")) {
			// 只返回本集团的账号的权限
			params.put("groupId", sessionUser.getCurrentJob().getGroupId());
		} else {
			// 不返回数据
			params.put("groupId", -1);
		}
		Long total = jobDao.count(params);
		Long pageNo = (Long) params.get("pageNo");
		Long pageSize = (Long) params.get("pageSize");
		params.put("start", (pageNo - 1) * pageSize);
		params.put("length", pageSize);
		List<User> list = jobDao.pageQueryAll(params);
		JsonResult result = JsonResult.success();
		result.put("total", total);
		result.put("list", list);
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("orderBy", params.get("orderByFrontend"));
		result.put("orderType", params.get("orderType"));
		if (pageSize != null) {
			result.put("totalPages", (total / pageSize) + (total % pageSize > 0 ? 1 : 0));
		}
		return result;
	}

	@CacheEvict(value = "users", allEntries = true)
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 60, rollbackFor = Exception.class)
	public JsonResult deleteUserJobRoles(String[] ids, User sessionUser) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		if (!ROOT_GROUP_ID.equals(sessionUser.getCurrentJob().getGroupId())) {
			// 非根系统用户只能删除自己所在集团的用户
			params.put("groupId", sessionUser.getCurrentJob().getGroupId());
		}
		params.put("lastModifierId", sessionUser.getUserId());
		params.put("lastModifierName", sessionUser.getRealname());
		params.put("jobId", sessionUser.getCurrentJobId());
		Long count = jobDao.deleteJobUserRoles(params);
		if (count != ids.length) {
			throw new RuntimeException("不能删除自己的配置");
		}
		// 删除配置，对应用户掉线
		List<String> userIds = jobDao.queryUserIdsByJobUserRoleIds(params);
		// 清理没有Roles配置的JobUserOrgPosition
		jobDao.deleteCleanJobUserOrgPosition(params);
		for (String userId : userIds) {
			SessionUtil.killAllSessionsByUserId(userId);
		}
		JsonResult result = JsonResult.success("删除成功");
		result.put("count", count);
		return result;
	}
}
