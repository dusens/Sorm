package test;

import java.sql.SQLException;

import bean.Article;
import bean.Comment;
import bean.User;
import util.DataBaseUtils;
import util.TableUtils;

public class TestMain {
	public static void main(String[] args) {
		String sql = TableUtils.getCreateTableSQl(Comment.class);
       System.out.println(sql);
        
        
	}
	
	
}
