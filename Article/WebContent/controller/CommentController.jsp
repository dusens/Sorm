<%@page import="bean.User"%>
<%@page import="bean.Comment"%>
<%@page import="service.CommentService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    User user = (User)session.getAttribute("user");
    if(user==null){//代表用户没有登陆
        out.print("-1");
        return;
    }else{
    		out.print("1");
    		
    }
    String txt = request.getParameter("txt").toString();
    String articleId = request.getParameter("articleId").toString();
     
    CommentService commentService = new CommentService();
    Comment comment = new Comment();
    comment.setId(UUID.randomUUID().toString());
    comment.setUserId(user.getId());
    comment.setArticleId(articleId);
    comment.setContent(txt);
    commentService.saveComment(comment);
     
     
%>