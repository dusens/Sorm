package test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import bean.Article;
import util.DataBaseUtils;



/**
 * 测试：给文章插入数据
 * JUnit是一个基于Java语言的单元测试框架，用起来比较方便。它的源代码很轻巧，
 * 而且优雅地运用了多种设计模式，应该来说，这是一个非常优秀的框架。
 * @author sensendu
 *
 */

public class TestInsertOperation {
	/**
	 * 给文章插入数据
	 */
	@Test
	public void insertArticle() {
		String sql = "INSERT INTO t_article(id,header,name,content,author,"
	            + "description,is_published,is_delete,create_time,update_time"
	            + ",user_id,category_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
	String id = UUID.randomUUID().toString(); //主键
	String header = "jdbc实用技术";
	String name  = "jdbc应用和封装工具类";
	String content = "应用类";
	String author = "鱼鱼鱼";
	String description = "解决jdbc链接mysql问题";
	int isPublished = 1 ;
	int isDelete = 0;
	String create_time = "2016-10-19 10:43:10";
	String update_time = "2016-10-19 10:43:10";
	String userId = "319600c3-550a-4f9f-80cf-deebe2376528";
	int categoryId = 2;
	DataBaseUtils.update(sql, id,header,name,content,author,description,isPublished,isDelete,create_time,update_time,userId,categoryId);
	System.out.println("新增成功！");

//	@Test
//	public void getArticle() throws SQLException{
//		String sql = "select * from t_article where id = ?";
//		Article article = DataBaseUtils.queryForBean(sql, Article.class, "f09a4fc8-48aa-44f6-b77b-699d9b3418a9");
//		System.out.println(article);
//	}
	/**
	 * 插入分类数据
	 */
//	@Test
//	public void insertCategory(){
//	    DataBaseUtils.update("insert into t_category set category_name = ?", "连载小说");
//	    DataBaseUtils.update("insert into t_category set category_name = ?", "编程代码类");
//	    DataBaseUtils.update("insert into t_category set category_name = ?", "生活感悟");
//	}

	/**
	 * 获取分类列表
	 */
//	@Test
//	public void getCategoryList(){
//	    String sql = "select * from t_category where 1 = 1";
//	    List list = DataBaseUtils.queryForList(sql);
//	    System.out.println(list);
//	}
	

	
	}
}
//}
