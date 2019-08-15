<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/header.jsp" />
<body onload="document.getElementById('defaultOpen').click();">
	<script type="text/javascript">
		function openTabs(evt, tabName) {

			let i, tabcontent, tablinks;

			tabcontent = document.getElementsByClassName("tabcontent");
			for (i = 0; i < tabcontent.length; i++) {
				tabcontent[i].style.display = "none";
			}

			tablinks = document.getElementsByClassName("tablinks");
			for (i = 0; i < tablinks.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" active", "");
			}

			document.getElementById(tabName).style.display = "block";
			evt.currentTarget.className += " active";

		}
		document.getElementById("defaultOpen").click();
	</script>

	<div class="grid-container">

		<div class="start-workout">
			<form action="../gymSessionLog" method="get">
				<button class="session-start-button">Start Workout!</button>
			</form>
		</div>
		<div class="member">
			<div class="avatar">
				<img src="${user.avatar}" alt="avatar image">
			</div>
			<div class="member-Links">
				<p>${user.name}</p>
				<p>Goal: ${user.workoutGoals}</p>
				<p>Profile: ${user.workoutProfile}</p>
			</div>
			<div class="admin-buttons">

				<p><a href="../addUser">Add Associate</a></p>
			</div>
			<div class="edit-profile-button">

				<p><a href="../editProfile">Edit Profile</a></p>

			</div>
			<div class="calendar-button">

				<p><a href="../calendar">View Class Calendar</a></p>

			</div>
		</div>
		<div class="member-analytics">
			<!-- Tab links -->
			<div class="tab">
				<button class="tablinks active" onclick="openTabs(event, 'Monthly')"
					id="defaultOpen">Monthly</button>
				<button class="tablinks" onclick="openTabs(event, 'Total')">Total</button>
				<button class="tablinks"
					onclick="openTabs(event, 'Scheduled-Classes')">Scheduled
					Classes</button>
			</div>

			<!-- Tab Monthly content -->
			<div id="Monthly" class="tabcontent">
				<canvas id="myChart" width="250" height="150"></canvas>
				<script>
					document.addEventListener("DOMContentLoaded", function() {
						var ctx = document.getElementById('myChart')
								.getContext('2d');
						var myChart = new Chart(ctx, {
							type : 'line',
							data : {
								labels : [ 'Week 1', 'Week 2', 'Week 3',
										'Week 4' ],
								datasets : [ {
									label : 'Consistency',
									data : [ 2, 4, 3, 5 ],
									backgroundColor : [
											'rgba(255, 99, 132, 0.2)',
											'rgba(54, 162, 235, 0.2)',
											'rgba(255, 206, 86, 0.2)',
											'rgba(75, 192, 192, 0.2)',
											'rgba(153, 102, 255, 0.2)',
											'rgba(255, 159, 64, 0.2)' ],
									borderColor : [ 'rgba(255, 99, 132, 1)',
											'rgba(54, 162, 235, 1)',
											'rgba(255, 206, 86, 1)',
											'rgba(75, 192, 192, 1)',
											'rgba(153, 102, 255, 1)',
											'rgba(255, 159, 64, 1)' ],
									borderWidth : 1
								} ]
							},
							options : {
								scales : {
									yAxes : [ {
										ticks : {
											beginAtZero : true
										}
									} ]
								}
							}
						});
					})
				</script>
				<br>
				<table>
					<tr>
						<th>Date</th>
						<th>Duration</th>
						<th>Sets</th>
						<th>Reps</th>
						<th>Weights</th>
					</tr>
					<c:forEach var="data" items="${gymSession}">
						<tr>
							<td>${data.date}</td>
							<td>${data.duration}</td>
							<td>${data.reps}</td>
							<td>${data.sets}</td>
							<td>${data.weights}</td>
						</tr>
					</c:forEach>
				</table>
			</div>

			<!-- Tab Total content -->
			<div id="Total" class="tabcontent">
				<table>
					<tr>
						<th>Date</th>
						<th>Start time</th>
						<th>End time</th>
						<th>Sets</th>
						<th>Reps</th>
						<th>Weights</th>
						<th>Equipment</th>
					</tr>
					<c:forEach var="equipment" items="${equipments}">
						<tr>
							<td>${equipment.date}</td>
							<td>${equipment.start}</td>
							<td>${equipment.end}</td>
							<td>${equipment.sets}</td>
							<td>${equipment.reps}</td>
							<td>${equipment.weights}</td>
							<td>${equipment.equipmentName}</td>
						</tr>

					</c:forEach>
				</table>
			</div>

			<!-- Tab Classes content -->
			<div id="Scheduled-Classes" class="tabcontent">
				<table>
					<tr>
						<th>Class</th>
						<th>Date</th>
						<th>Start time</th>
						<th>End time</th>
					</tr>
					<c:forEach var="workoutClass" items="${classesScheduled}">
						<tr>
							<td>${workoutClass.workoutClassName}</td>
							<td>${workoutClass.classDate}</td>
							<td>${workoutClass.classStartTime}</td>
							<td>${workoutClass.classEndTime}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<c:import url="/WEB-INF/jsp/footer.jsp" />