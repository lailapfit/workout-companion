<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {

		$("form").validate({

			rules : {
				userName : {
					required : true
				},
				password : {
					required : true
				}
			},
			messages : {
				confirmPassword : {
					equalTo : "Passwords do not match"
				}
			},
			errorClass : "error"
		});
	});
</script>
<div class="container">
	<div class="login-header">
		<div class="login">
			<c:url var="formAction" value="/login" />
			<form method="POST" action="${formAction}">
				<input type="hidden" name="destination" value="${param.destination}" />
				<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />

				<div class="synergyLogo">
					<img src="img/workout-logo.png">
				</div>

				<div class="loginTextField">
					<div>
						<input type="text" id="userName" name="userName"
							placeHolder="User Name" class="userNameInput" />
					</div>
					<div>
						<input type="password" id="password" name="password"
							placeHolder="Password" class="passwordInput" />

					</div>
					<div>
						<button type="submit" class="loginSubmitBTN">Login</button>
					</div>
				</div>
			</form>
		</div>

		<span class="loginHeaderGroup">
			<div class="loginHeader">
				<h1>THE ULTIMATE WORKOUT COMPANION</h1>
				<h2>Let us help you get in the best shape of your life.</h2>
			</div> <c:url var="action" value="/users/new" />
			<form method="GET" action="${action}">
				<button type="submit" class="loginSignInBTN">Sign Up Now</button>
			</form>
		</span>
	</div>
</div>

<div class="stonesEndorsment">
	<h2>
		<i>"Synergy, a new hope for American Fitness" <br> -Rolling
			Stones Magazine-
		</i>
	</h2>
</div>

<c:import url="/WEB-INF/jsp/footer.jsp" />