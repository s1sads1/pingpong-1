package kh.pingpong.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
}