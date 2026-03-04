// frontend/js/api.js
const BASE_URL = "http://localhost:8080";

function getToken() {
  return sessionStorage.getItem("token");
}

function requireAuth() {
  const token = getToken();
  if (!token) {
    window.location.href = "login.html";
  }
}

function logout() {
  sessionStorage.removeItem("token");
  sessionStorage.removeItem("username");
  sessionStorage.removeItem("role");
  window.location.href = "login.html";
}

async function apiFetch(path, options = {}) {
  const url = BASE_URL + path;

  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {})
  };

  const res = await fetch(url, { ...options, headers });

  // handle non-JSON responses safely
  const text = await res.text();
  let data = null;
  try { data = text ? JSON.parse(text) : null; } catch { data = text; }

  if (!res.ok) {
    const msg = (data && data.message) ? data.message : "Request failed";
    throw new Error(msg);
  }
  return data;
}