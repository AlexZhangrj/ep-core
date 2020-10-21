package com.zhrenjie04.alex.manager.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhrenjie04.alex.core.JsonResult;
import com.zhrenjie04.alex.core.User;
import com.zhrenjie04.alex.manager.dao.JobUserOrgPositionDao;
import com.zhrenjie04.alex.manager.dao.JobUserRoleDao;
import com.zhrenjie04.alex.manager.dao.OrgDao;
import com.zhrenjie04.alex.manager.dao.PositionDao;
import com.zhrenjie04.alex.manager.dao.RoleDao;
import com.zhrenjie04.alex.manager.domain.Config;
import com.zhrenjie04.alex.manager.domain.JobUserOrgPosition;
import com.zhrenjie04.alex.manager.domain.JobUserRole;
import com.zhrenjie04.alex.manager.domain.Org;
import com.zhrenjie04.alex.manager.domain.Position;
import com.zhrenjie04.alex.manager.domain.Role;
import com.zhrenjie04.alex.util.IdGenerator2;
import com.zhrenjie04.alex.util.StringUtil;

/**
 * @author 张人杰
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	JobUserOrgPositionDao userOrgPositionDao;

	@Autowired
	JobUserRoleDao jobUserRoleDao;

	@Autowired
	OrgDao orgDao;

	@Autowired
	PositionDao positionDao;

	@Autowired
	RoleDao roleDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 60, rollbackFor = Exception.class)
	public JsonResult saveConfig(Config config, User user) {
		if(StringUtil.isEmpty(config.getOrgId())) {
			throw new RuntimeException("请选择组织结构");
		}
		if(StringUtil.isEmpty(config.getPositionId())) {
			throw new RuntimeException("请选择职位");
		}
		if(config.getRoleIds()==null||config.getRoleIds().size()==0) {
			throw new RuntimeException("请选择角色");
		}
		// 不能配置自己的账号
		if (config.getUserIds().contains(user.getUserId())) {
			throw new RuntimeException("不能配置自己的账号！");
		}
		if (!"0".equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户，只能配置自己集团的职位
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("orgId", config.getOrgId());
			Org org = orgDao.queryObject(params);
			if (!org.getGroupId().equals(user.getCurrentJob().getGroupId())) {
				throw new RuntimeException("只能配置自己所在集团的组织机构！");
			}
		}
		if (!"0".equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户，只能配置自己集团的职位
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("id", config.getPositionId());
			Position position = positionDao.queryObject(params);
			if (!position.getGroupId().equals(user.getCurrentJob().getGroupId())) {
				throw new RuntimeException("只能配置自己所在集团的职位！");
			}
		}
		// 所有用户只能配置自己角色之下的角色（包括自己的角色）
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ids", config.getRoleIds());
		List<Role> roles = roleDao.queryAll(params);
		boolean isOk = true;
		roleLoop: for (Role role : roles) {
			boolean found = false;
			for (String roleId : user.getCurrentRoleIds()) {
				if ("0".equals(roleId)) {
					isOk = true;
					break roleLoop;
				}
				if (role.getIdPath().contains("/"+roleId+"/")) {
					found = true;
					break;
				}
			}
			if (found == false) {
				isOk = false;
				break;
			}
		}
		if(!isOk) {
			throw new RuntimeException("只能配置自己拥有的角色或下级角色！");
		}
		// 以下保存入库
		for (String userId : config.getUserIds()) {
			params.clear();
			params.put("userId", userId);
			params.put("orgId", config.getOrgId());
			params.put("positionId", config.getPositionId());
			JobUserOrgPosition job = userOrgPositionDao.queryObject(params);
			params.put("lastModifierId", user.getUserId());
			params.put("lastModifierName", user.getRealname());
			userOrgPositionDao.deleteAll(params);
			if (job != null) {
				params.clear();
				params.put("userId", userId);
				params.put("jobId", job.getJobId());
				params.put("lastModifierId", user.getUserId());
				params.put("lastModifierName", user.getRealname());
				jobUserRoleDao.deleteAll(params);
			}
			job = new JobUserOrgPosition();
			job.setJobId(IdGenerator2.nextIdBase52String());
			job.setUserId(userId);
			job.setOrgId(config.getOrgId());
			job.setPositionId(config.getPositionId());
			job.setCreaterId(user.getUserId());
			job.setCreaterName(user.getRealname());
			userOrgPositionDao.insertObject(job);
			for (String roleId : config.getRoleIds()) {
				JobUserRole jobUserRole = new JobUserRole();
				jobUserRole.setId(IdGenerator2.nextIdBase52String());
				jobUserRole.setJobId(job.getJobId());
				jobUserRole.setUserId(userId);
				jobUserRole.setRoleId(roleId);
				jobUserRoleDao.insertObject(jobUserRole);
			}
		}
		JsonResult result = JsonResult.success();
		return result;
	}
	@Override
	public JsonResult loadConfig(Config config, User user) {
		// 不能配置自己的账号
		if (config.getUserIds().contains(user.getUserId())) {
			throw new RuntimeException("不能配置自己的账号！");
		}
		if (!"0".equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户，只能配置自己集团的职位
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("orgId", config.getOrgId());
			Org org = orgDao.queryObject(params);
			if (!org.getGroupId().equals(user.getCurrentJob().getGroupId())) {
				throw new RuntimeException("只能配置自己所在集团的组织机构！");
			}
		}
		if (!"0".equals(user.getCurrentJob().getGroupId())) {
			// 非根系统用户，只能配置自己集团的职位
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("id", config.getPositionId());
			Position position = positionDao.queryObject(params);
			if (!position.getGroupId().equals(user.getCurrentJob().getGroupId())) {
				throw new RuntimeException("只能配置自己所在集团的职位！");
			}
		}
		// 加载角色列表
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("userIds", config.getUserIds());
		params.put("orgId", config.getOrgId());
		params.put("positionId", config.getPositionId());
		List<String>jobIds=userOrgPositionDao.queryAllJobIds(params);
		if(jobIds.size()>0) {
			params.clear();
			params.put("jobIds", jobIds);
			List<Role>roles = jobUserRoleDao.queryAllRolesByJobIds(params);
			JsonResult result = JsonResult.success();
			result.put("roles", roles);
			return result;
		}else {
			throw new RuntimeException("没有原始配置数据");
		}
	}
}
