package com.blog.ssh.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CommonDao<T> {
	/**
	 * 保存对象
	 * @param entity
	 */
	void save(T entity) throws Exception;
	/**
	 * 根据主键删除对象
	 * @param ids
	 */
	void deleteObjectByIDs(Serializable... ids)throws Exception;
	/**
	 * 更新对象
	 * @param entity
	 */
	void update(T entity)throws Exception;
	/**
	 * 根据id查找对象
	 * @param id
	 * @return
	 */
	T findObjectById(Serializable id)throws Exception;
	/**
	 * 根据条件查询对象集合
	 * @param condition 查询语句
	 * @param params 参数数组
	 * @param orderby 根据字段排序
	 * @return
	 */
	List<T> findObjectCollectionByConditionWithNoPage(String condition,Object[] params,Map<String,String> orderby)throws Exception;
}
