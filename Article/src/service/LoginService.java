package service;
import java.sql.SQLException;

import bean.User;
import util.DataBaseUtils;

/**
 * 用户登录的服务类
 * @author du
 *
 */
public class LoginService {
	
	public User getUser(String username) throws SQLException{
		String sql = "select * from t_user where username = ?";
		User user = DataBaseUtils.queryForBean(sql, User.class, username);
		if(user == null){
			return null;
		}
		//System.out.println(user);
		return user;
	}
	
}
