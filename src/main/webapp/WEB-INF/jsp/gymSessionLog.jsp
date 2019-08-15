<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/header.jsp" />

<div class="gym-log">
	<div class="session-call-to-action">
	<c:url var="formAction" value="/gymSessionEnd" />
	<form method="GET" action="${formAction}">
		<button type="submit" class="end-session-button">End Workout!</button>
		</form>
	</div>
	
<!-- menu of workouts starts here -->
	<c:url var="formAction" value="/gymSessionLog" />
	<c:forEach var="machine" items="${equipment}">
		<form method="POST" action="${formAction}">
		<div class="session">
				<iframe width="400" height="300"
					src="${machine.video}" frameborder="0"
					allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
					allowfullscreen></iframe>
		<div class="session-machine">
		<div class="equipment-description">
			<div class="machine-name-muscle">
			<c:out value="${machine.name}" /> - <c:out value="${machine.muscleGroup}" /><br><br></div>
			<c:out value="${machine.description}" />

			<input type="hidden" name="equipmentId" value="${machine.equipment_id}" /></div>
			<table class="per-session">
				<tr>
					<th><label for="sets">Sets: </label></th>
					<td><input type="text" name="sets" placeHolder="Sets" class="set-table"/></td>
				</tr>
				<tr>
					<th><label for="reps">Reps: </label></th>
					<td><input type="text" name="reps" placeHolder="Reps" class="set-table" /></td>
				</tr>
				<tr>
					<th><label for="weight">Weight: </label></th>
					<td><input type="text" name="weight" placeHolder="Weight" class="set-table"/></td>
				</tr>
				</table>
					<button type="submit" class="add-session-button">Add Session</button>
			</div>
			</div>
		</form>
	</c:forEach>
</div>

<c:import url="/WEB-INF/jsp/footer.jsp" />