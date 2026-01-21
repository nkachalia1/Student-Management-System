let currentUser, role;

function login() {
  role = roleSelect().value;
  const u = val("username");
  const p = val("password");

  const pool = role==="student"?students:role==="professor"?professors:admins;
  currentUser = pool.find(x=>x.username===u && x.password===p);

  if(!currentUser) return alert("Invalid login");

  renderMenu();
}

function renderMenu() {
  let m = document.getElementById("menu");
  m.innerHTML = `<h3>${role.toUpperCase()} MENU</h3>`;

  if(role==="student") {
    m.innerHTML += `
      <button onclick="viewCourses()">View Courses</button>
      <button onclick="addCourse()">Add Course</button>
      <button onclick="dropCourse()">Drop Course</button>
      <button onclick="viewGrades()">View Grades</button>`;
  }

  if(role==="professor") {
    m.innerHTML += `
      <button onclick="viewAssigned()">View Assigned</button>
      <button onclick="assignGrades()">Assign Grades</button>`;
  }

  if(role==="admin") {
    m.innerHTML += `<button onclick="viewAll()">View All Users</button>`;
  }
}

function viewCourses() {
  content(currentUser.courses.join("<br>") || "No courses");
}

function addCourse() {
  const id = prompt("Course ID");
  const c = courses.find(c=>c.id===id);
  if(!c) return alert("Not found");
  if(c.students.length>=c.cap) return alert("Course full");
  currentUser.courses.push(id);
  c.students.push(currentUser.id);
}

function dropCourse() {
  const id = prompt("Course ID");
  currentUser.courses = currentUser.courses.filter(c=>c!==id);
}

function viewGrades() {
  content(Object.entries(currentUser.grades).map(([c,g])=>`${c}: ${g}`).join("<br>"));
}

function viewAssigned() {
  content(currentUser.assigned.join("<br>"));
}

function assignGrades() {
  const id = prompt("Course ID");
  students.forEach(s=>{
    if(s.courses.includes(id)) {
      s.grades[id] = prompt(`Grade for ${s.name}`);
    }
  });
}

function viewAll() {
  content(
    `<h4>Students</h4>`+students.map(s=>s.name).join("<br>")+
    `<h4>Professors</h4>`+professors.map(p=>p.name).join("<br>")
  );
}

function content(html){document.getElementById("content").innerHTML=html;}
function val(id){return document.getElementById(id).value;}
function roleSelect(){return document.getElementById("role");}
