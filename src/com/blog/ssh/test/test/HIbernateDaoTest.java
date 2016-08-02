package com.blog.ssh.test.test;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;

import com.blog.ssh.test.domain.TestTable;

public class HIbernateDaoTest {

	SessionFactory sessionFactory;
	
	@Before
	public void init(){
		Configuration config = new Configuration();
		config.configure();//默认加载类路径下的hibernate.cfg.xml
		sessionFactory = config.buildSessionFactory();
	}
	
	@Test
	public void testAdd(){
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		TestTable testTable = new TestTable();
		testTable.setName("测试hibernateDao");
		
		session.save(testTable);
		
		transaction.commit();
	}
}
