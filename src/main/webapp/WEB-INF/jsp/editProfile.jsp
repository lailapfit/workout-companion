<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/jsp/header.jsp" />

<div class="newUserContainer">
	<c:url var="formAction" value="/updateProfile" />
	<form method="POST" action="${formAction}">
		<input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}" />
		<div class="newUserSignUpHeaders">
			<h1>Update your fitness goals!</h1>
			<div class="synergyLogoEP">
				<img src="img/workout-logo.png">
			</div>
		</div>
		<div>
			<input type="text" id="workoutGoals" name="workoutGoals"
				placeHolder="Workout Goals" class="newUserWorkoutGoals" />
		</div>
		<div>
			<button type="submit" class="editProfileButton">Edit Profile</button>
		</div>
	</form>
</div>


<c:import url="/WEB-INF/jsp/footer.jsp" />