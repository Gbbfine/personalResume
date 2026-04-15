import { TOKEN_KEY, getApiBase } from "./config.js";

export function getToken() {
  return window.localStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
  window.localStorage.setItem(TOKEN_KEY, token);
}

export function clearToken() {
  window.localStorage.removeItem(TOKEN_KEY);
}

export async function request(path, options = {}, requireAuth = false) {
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {})
  };

  if (requireAuth) {
    const token = getToken();
    if (!token) throw new Error("请先登录");
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${getApiBase()}${path}`, {
    ...options,
    headers
  });

  const json = await response.json().catch(() => ({}));
  if (!response.ok || json.code !== 0) {
    throw new Error(json.message || `请求失败: ${response.status}`);
  }
  return json.data;
}
