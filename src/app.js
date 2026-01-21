let currentUser = null;
let currentRole = null;

function login() {
  const role = roleSelect().value;
  const username = val("username");
  const password = val("password");

  let userList = role === "student" ? students :
                 role === "professor" ? professors :
                 admins;

  const user = userList.find(u => u.username === username && u.password === password);

  if (!user) {
    alert("Invalid credentials");
    return;
  }

  currentUser = user;
  currentRole = role;
  renderMenu();
}

function renderMenu() {
  const menu = document.getElementById("menu");
  menu.innerHTML = `<h3>${currentRole.toUpperCase()} MENU</h3>`;

  if (currentRole === "student") {
    menu.innerHTML += `
      <button onclick="viewStudentCourses()">View Courses</button>
      <button onclick="addCourse()">Add Course</button>
      <button onclick="dropCourse()">Drop Course</button>
      <button onclick="viewGrades()">View Grades</button>
    `;
  }

  if (currentRole === "professor") {
    menu.innerHTML += `
      <button onclick="viewAssigned()">View Assigned Courses</button>
      <button onclick="assignGrades()">Assign Grades</button>
    `;
  }

  if (currentRole === "admin") {
    menu.innerHTML += `
      <button onclick="viewAllUsers()">View All Users</button>
    `;
  }
}

function viewStudentCourses() {
  content(coursesOf(currentUser).map(c => c.id).join("<br>"));
}

function addCourse() {
  const id = prompt("Enter course ID");
  const course = findCourse(id);
  if (!course) return alert("Not found");

  if (course.students.length >= course.capacity)
    return alert("Course full");

  course.students.push(currentUser.id);
  currentUser.courses.push(course.id);
}

function dropCourse() {
  const id = prompt("Course ID");
  currentUser.courses = currentUser.courses.filter(c => c !== id);
  findCourse(id).students =
    findCourse(id).students.filter(s => s !== currentUser.id);
}

function viewGrades() {
  content(Object.entries(currentUser.grades)
    .map(([c, g]) => `${c}: ${g}`).join("<br>"));
}

function viewAssigned() {
  content(currentUser.assigned.join("<br>"));
}

function assignGrades() {
  const cid = prompt("Course ID");
  students.forEach(s => {
    if (s.courses.includes(cid)) {
      s.grades[cid] = prompt(`Grade for ${s.name}`);
    }
  });
}

function viewAllUsers() {
  content(
    `<h4>Students</h4>` +
    students.map(s => s.name).join("<br>") +
    `<h4>Professors</h4>` +
    professors.map(p => p.name).join("<br>")
  );
}

// Helpers
function content(html) {
  document.getElementById("content").innerHTML = html;
}

function val(id) {
  return document.getElementById(id).value;
}

function roleSelect() {
  return document.getElementById("role");
}

function findCourse(id) {
  return courses.find(c => c.id === id);
}

function coursesOf(student) {
  return courses.filter(c => student.courses.includes(c.id));
}
