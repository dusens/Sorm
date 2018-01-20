package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
	
	static Properties  pros = null;
	static {
		pros = new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Connection getMysqlCon() {
		try {
			Class.forName(pros.getProperty("db.Driver"));
			return DriverManager.getConnection(pros.getProperty("db.Url"),pros.getProperty("db.username"),pros.getProperty("db.password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void close(ResultSet rs,PreparedStatement ps,Connection conn) {
		try {
			if(rs!=null) {
			rs.close();
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			if(ps!=null) {
			ps.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			if(conn!=null) {
			conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			if(rs!=null) {
			rs.close();
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	}
	public static void close(PreparedStatement ps) {
		try {
			if(ps!=null) {
			ps.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public static void close(Connection conn) {
		try {
			
			if(conn!=null) {
			conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
}
