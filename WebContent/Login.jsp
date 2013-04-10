<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Login</title>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-lightbox.min.css" />
<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="css/2730_project/Login.css" />

<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-lightbox.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/2730_project/Login.js"></script>

</head>
<body>
	<div class="" id="loginModal">
		<div class="modal-body">
			<div class="well">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#login" data-toggle="tab">Login</a></li>
					<li><a href="#create" data-toggle="tab">Create Account</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane active in" id="login">
						<form id="loginForm" action='/2730_project/Login' method="POST">
							<!-- Username -->
							<div>
								<label>Username</label> <input type="text" id="username"
									name="username" placeholder="" class="input-xlarge">
							</div>
							<div>
								<!-- Password-->
								<label>Password</label> <input type="password" id="password"
									name="password" placeholder="" class="input-xlarge">
							</div>
							<!-- Button -->
							<div style="margin-top: 13px;">
								<input type="submit" value="Login" class="btn btn-success" />
								<span class="formMessage">
									<img class="loadingIcon hidden" src="img/ajax-loader.gif"/>
									<i class="icon-ok-sign hidden"></i>
									<i class="icon-remove-sign hidden"></i>									
									<span class="formMessageText hidden"></span>
								</span>
							</div>
						</form>
					</div>
					<div class="tab-pane fade" id="create">
						<form id="registerForm" action="/2730_project/Register" type="POST">
							<label>Username</label> <input type="text" value=""
								name="userName" class="input-xlarge"> <label>Password</label>
							<input type="password" value="" name="password"
								class="input-xlarge"> <label>Confirm Password</label> <input
								type="password" value="" name="confirmPassword"
								class="input-xlarge">
							<div>
								<input style="margin-top: 13px;" type="submit"
									class="btn btn-primary" value="Create Account" />
								<span class="formMessage">
									<img class="loadingIcon hidden" src="img/ajax-loader.gif"/>
									<i class="icon-ok-sign hidden"></i>
									<i class="icon-remove-sign hidden"></i>		
									<span class="formMessageText hidden"></span>
								</span>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
</body>
</html>