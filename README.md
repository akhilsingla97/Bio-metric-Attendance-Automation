# Bio-metric-Attendance-Automation
                        
<h2>Introduction</h2>

<b>Automated attendance system using biometrics</b> is the best replacement to bulky, time consuming manually fed attendance system. Automated attendance system is considered to be the most efficient and trustworthy way and has a noteworthy impact. It offers support services to fulfil the back office needs of departments. All this is done by automation hence the human effort and error is reduced.

We plan to build fingerprint based attendance system for our college:
<ul>
<li>An app to utilize the fingerprint scanner, available in the market, to mark attendance.
<li>Fingerprint scanner to be attached to a mobile phone with OTG support.
<li>App to have cloud support, so that the teacher can directly download the attendance from the cloud, whenever he/she wishes to.
<li>Fingerprint of the teacher activates the device and then the device gets circulated in the class, just like a sheet is circulated in the traditional attendance system.
<li>Using this method, first of all, chances of proxy get eliminated, secondly the whole process becomes less cumbersome.
<li>Overall, process gets automated reducing manual effort.
</ul>

<h2>Proposed procedure for the app:</h2>

1- Administration makes professor and student database,so that only authentic person may use the app. Their job is to add/remove new faculty/student and making sure that correct courses are allocated to correct faculty,and correct list of students enrolled in all courses is present.

2- Professor logs in with a professorID and password.

3- Gets the options in the dashboard :<br>
  a) Add/Remove course<br>
  b) Enroll students to the course<br>
  c) Take attendance<br>
  d) View attendance<br> 
  e) Change password<br>

<h3>Explanation:<h3>

3a-> This gives option to select courses undertaken by the professor in the semester.

3b-> When a course is selected, a list of students from that appears with a checkbox against their name<br>
-> In case of stuent not taking the course, teacher should be able to remove the tick<br>
-> Also for any senior with backlog, there should be an option to import student from the other year

3c-> Once the students are enrolled, she should be able to take attendance for the course. The attendance data of that course gets updated.<br> 
-> Teacher may be given a special privilege to mark a student present, but with some reason attributed to it<br>
-> There should be an option of submit attendance, but it should ask for password again because the device would be circulating the class,so only teacher should be able to close it.<br>

3d-> View attendance shows the attendance for the course, with students less than 75% highlighted

3e-> It is a basic funtionality that allows faculty to change the password.

<h2>Database design:</h2>
<b>Corresponding to each course:</b> - StudentID, FingerprintID, attendance count + a column added each time attendance is being marked<br>
<b>Professors:</b> ProfID, password, + other details<br>
<b>Courses:</b> courseID, coursename, ProfID (fk) <br>
<b>students:</b> StudentID, FingerprintID, studentname, year, branch, + other details<br>                        

We are making separate database for each course, so that the fingerprint mapping occurs only for that particular course enrolled students, hence improving efficiency
