<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="service.ArticleService"%>
<% ArticleService articleService = new ArticleService(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include  file="common/taglib.jsp"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<script src="${basePath}/static/js/jQuery.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/static/css/index.css"/>

<style>


</style>

</head>
<body>

<!-- 头部页面 -->
<%@include file="common/header.jsp" %>

<!-- banner区 -->
<div class="banner">
	<div class='content'>
		<!-- 轮播图 -->
		<ul>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/5.png "/>
				</a>
			</li>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/1.png "/>
				</a>
			</li>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/2.png "/>
				</a>
			</li>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/3.png "/>
				</a>
			</li>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/4.png "/>
				</a>
			</li>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/5.png "/>
				</a>
			</li>
			<li class='fl'>
				<a href="javascript:void(0)">
					<img src="${basePath}/static/img/1.png "/>
				</a>
			</li>
		</ul>
		<!-- 按钮区 -->
		<span class='banner_left'><i class='btn_left'></i></span>
		<span class='banner_right'><i class='btn_right'></i></span>
</div>

</div>

<!-- 内容区域（待做） -->
<div style='border:1px solid #ccc'>
	<br/><br/>
	<div class='category'>
		<div class='title'>连载小说</div>
		<ul class='items'>
		
		<li class='item'>
			<div class='item-banner'>
				<div class='item-header'>生活中总是充满了乐趣</div>
				<div class='item-name'>聊聊我的大学室友</div>
				<div class='item-author'>@张三 著</div>
			</div>
			<div class='item-description'>那些回忆，那些事。。。</div>
		</li>
			
			<li class='item'></li>
			<li class='item'></li>
			<div style='clear:both'></div>
		</ul>
	</div>
	
	
	<%
		//查询出编程代码类的相关文章
		List<Map<String,Object>>  articles2 = articleService.getArticlesByCategoryId(2, 0, 6);
		pageContext.setAttribute("articles2", articles2);
	%>
	
	<div class='category'>
		<div class='title'>编程代码类</div>
		<ul class='items'>
			<c:forEach items="${articles2}" var="item">
				<li class='item' onclick="detail('${item.id}');">
					<div class='item-banner'>
						<div class='item-header'>${item.header}</div>
						<div class='item-name' title = "${item.name}">${item.name}</div>
						<div class='item-author'>@${item.author} 著</div>
					</div>
					<div class='item-description'>${item.description}</div>
				</li>
			</c:forEach>
			<div style='clear:both'></div>
		</ul>
	</div>
	
	<div class='category'>
		<div class='title'>干货分享</div>
		<ul class='items'>
			<li class='item'></li>
			<li class='item'></li>
			<li class='item'></li>
			<div style='clear:both'></div>
		</ul>
	</div>

</div>

<!-- 底部页面 -->
<%@include file="common/footer.jsp" %>

<script>
	var leftBtn = $('.btn_left').eq(0); //左按钮
	var rightBtn = $('.btn_right').eq(0);//右按钮
	
	var ul = $('.banner .content ul').eq(0); 
	
	var index = 0;
	var timer = null; 
	
	var imgwidth = $('.banner .content ul li').width(); //获取轮播图片的宽度
	var len =  $('.banner .content ul li').length - 2//获取总共的图片数量
	
	//下一张
	rightBtn.on('click',function(){
		clearTimeout(timer); //定时器清零
		timer = setTimeout(function(){
			index ++;
			movePicture();
		},500);
		
	});
	
	//上一张
	leftBtn.on('click',function(){
		clearTimeout(timer); //定时器清零
		timer = setTimeout(function(){
			index --;
			movePicture();
		},500);
	});
			
	//移动图片
	function movePicture(){
		$('.banner .content ul').animate({'margin-left':-imgwidth * (index+1)},1000,function(){
			if(index == len){
				$(this).css('margin-left',-imgwidth);
				index = 0;
			}
			if(index == -1){
				$(this).css('margin-left',-imgwidth * len);
				index = len - 1;
			}
		});
	}
	
	
	
	//打开详情页
	function detail(id){
		var a = document.createElement("a");
		a.href = "detail.jsp?id=" + id; 
		console.log(a);
		a.target = '_new'; //指定在新窗口打开
		a.click();//触发打开事件
	}
	
</script>

</body>
</html>