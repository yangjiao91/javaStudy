<%@ page contentType="text/html;charset=UTF-8"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>bestpay - BESTPAY系统</title>

</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#"><img src="themes/default/images/login_logo.gif" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="javascript:;" title="收藏！" id="fav">收藏</a></li>
						<li><a href="mailto:team-hyacinth@funshion.com?subject=[bestpay]意见反馈">反馈</a></li>
					</ul>
				</div>
				<h2 class="login_title">
					<img src="themes/default/images/login_title.png" />
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="login" method="post" >
				
					<p>
						<label>用户名：</label>
						<input type="text" name="username" size="20" class="login_input" />
					</p>
					<p>
						<label>密码：</label>
						<input type="password" name="password" size="20" class="login_input" />
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
						<s:if test='errMsg != null'>
							<font color='red'>${errMsg}</font><p>
						</s:if>
					</div>
				</form>
			</div>
			<div class="login_banner">
				<img src="themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
				<ul class="helpList">
					<li><a href="#">忘记密码怎么办？</a></li>
					<li><a href="#">为什么登录失败？</a></li>
				</ul>
				<div class="login_inner">
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>