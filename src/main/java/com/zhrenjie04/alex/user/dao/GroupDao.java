package com.zhrenjie04.alex.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.manager.domain.Group;

/**
 * @author 张人杰
 */
@Repository("groupDao")
public interface GroupDao {
	Group queryObjectById(String groupId);
}
