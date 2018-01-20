package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import bean.Comment;
import util.DataBaseUtils;

public class CommentService {
	
	/**
	 * 保存评论
	 */
	public void saveComment(Comment comment){
	    String sql = "insert into t_comment(id,user_id,content,article_id,create_time,is_delete) values(?,?,?,?,?,?)";
	    DataBaseUtils.update(sql,comment.getId(),comment.getUserId(),
	    		comment.getContent(),comment.getArticleId(),new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),0);
	}
	
	/**
	 * 根据文章id查询它的所有评论
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getCommentsByArticleId(String id){
		return DataBaseUtils.queryForList("select b.username ,a.content from t_comment a left JOIN t_user b " +
				 "on a.user_id = b.id  where a.article_id = ?", id);
	}

	
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		
		CommentService cs = new CommentService();
		System.out.println(cs.getCommentsByArticleId("fb6b70ea-4023-45a3-9106-561649803805"));
		
		
//		Comment comment = new Comment();
//		comment.setId(UUID.randomUUID().toString());
//		comment.setContent("很不错的文章，赞一个！");
//		comment.setArticleId("fb6b70ea-4023-45a3-9106-561649803805");
//		comment.setUserId("319600c3-550a-4f9f-80cf-deebe2376528");
//		cs.saveComment(comment);
//		System.out.println("保存成功！");
//		System.out.println(DataBaseUtils.queryForList("select a.content from t_comment a left JOIN t_user b "
//				+ "on a.user_id = b.id where a.article_id = 'fb6b70ea-4023-45a3-9106-561649803805'"));
		

	}
}