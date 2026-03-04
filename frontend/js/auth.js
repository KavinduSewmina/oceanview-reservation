async function doLogin() {
  const usernameOrEmail = document.getElementById("user").value.trim();
  const password = document.getElementById("pass").value.trim();
  const msg = document.getElementById("msg");
  msg.innerHTML = "";

  if (!usernameOrEmail || !password) {
    msg.innerHTML = `<div class="msg err">Please enter username/email and password.</div>`;
    return;
  }

  try {
    const data = await apiFetch("/api/auth/login", {
      method: "POST",
      body: JSON.stringify({ usernameOrEmail, password })
    });

    sessionStorage.setItem("token", data.token);
    sessionStorage.setItem("username", data.username);
    sessionStorage.setItem("role", data.role);

    window.location.href = "reservation.html";
  } catch (e) {
    msg.innerHTML = `<div class="msg err">${e.message}</div>`;
  }
}

function fillDemo(){
  document.getElementById("user").value = "admin";
  document.getElementById("pass").value = "admin123";
}