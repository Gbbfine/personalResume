import { request, setToken } from "./apiClient.js";

const form = document.getElementById("loginForm");
const msg = document.getElementById("message");

form.addEventListener("submit", async (e) => {
  e.preventDefault();
  msg.textContent = "登录中...";

  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value;

  try {
    const data = await request("/api/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password })
    });

    setToken(data.token);
    msg.textContent = "登录成功，正在跳转...";
    location.href = "./dashboard.html";
  } catch (error) {
    msg.textContent = `登录失败：${error.message}`;
  }
});