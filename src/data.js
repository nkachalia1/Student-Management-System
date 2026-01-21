let students = [];
let professors = [];
let admins = [];
let courses = [];

async function loadData() {
  await loadCourses();
  await loadAdmins();
  await loadProfessors();
  await loadStudents();
}

async function loadCourses() {
  const text = await fetch("courseInfo.txt").then(r => r.text());
  text.trim().split("\n").forEach(line => {
    const [id,name,lecturer,days,start,end,cap] = line.split(";").map(s=>s.trim());
    courses.push({id,name,lecturer,days,start,end,cap:+cap,students:[]});
  });
}

async function loadAdmins() {
  const text = await fetch("adminInfo.txt").then(r => r.text());
  text.trim().split("\n").forEach(l => {
    const [id,name,user,pass] = l.split(";").map(s=>s.trim());
    admins.push({id,name,username:user,password:pass});
  });
}

async function loadProfessors() {
  const text = await fetch("profInfo.txt").then(r => r.text());
  text.trim().split("\n").forEach(l => {
    const [name,id,user,pass] = l.split(";").map(s=>s.trim());
    professors.push({id,name,username:user,password:pass,assigned:[]});
  });
}

async function loadStudents() {
  const text = await fetch("studentInfo.txt").then(r => r.text());
  text.trim().split("\n").forEach(l => {
    const [id,name,user,pass] = l.split(";").map(s=>s.trim());
    students.push({id,name,username:user,password:pass,courses:[],grades:{}});
  });
}

loadData();
