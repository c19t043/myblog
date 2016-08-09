package com.blog.ssh.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.blog.ssh.base.dao.CommonDao;
import com.blog.ssh.utils.TUtil;

@SuppressWarnings("all")
public class CommonDaoImpl<T> extends HibernateDaoSupport implements CommonDao<T> {
	
	Class entityClass = TUtil.getActualType(this.getClass());	

	/**
	 * 注入sessionFactory
	 * @param sessionFactory
	 */
	@Resource(name="sessionFactory")
	public void setDI(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 保存对象
	 * @param entity
	 */
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	/**
	 * 根据主键删除对象
	 * @param ids
	 */
	public void deleteObjectByIDs(Serializable... ids) {
		if(ids!=null&&ids.length>0){
			for(Serializable id : ids){
				Object entity = this.findObjectById(id);
				this.getHibernateTemplate().delete(entity);
			}
		}
	}

	/**
	 * 更新对象
	 * @param entity
	 */
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}
	/**
	 * 根据id查找对象
	 * @param id
	 * @return
	 */
	public T findObjectById(Serializable id) {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 根据条件查询对象集合
	 * @param condition 查询语句
	 * @param params 参数数组
	 * @param orderby 根据字段排序
	 * @return
	 */
	public List<T> findObjectCollectionByConditionWithNoPage(String condition,
			final Object[] params, Map<String, String> orderby) {
		String oderByCondition = getOrderByCondition(orderby);
		final String finallyHql = condition+oderByCondition; 
		return this.getHibernateTemplate().find(finallyHql, params);
		/*return	(List<T>) this.getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(finallyHql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i+1, params[i]);
				}
				return (T) query.list();
			}
		});*/
	}
	
	private String getOrderByCondition(Map<String, String> orderby){
		StringBuilder sb = new StringBuilder();
		if(orderby!=null&&orderby.size()>0){
			sb.append(" order by ");
			for (Entry<String, String> entry : orderby.entrySet()) {
				sb.append(entry.getKey()).append(" "+entry.getValue()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
