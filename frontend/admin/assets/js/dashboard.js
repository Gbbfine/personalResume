import { clearToken, getToken, request } from "./apiClient.js";
import { getApiBase, setApiBase } from "./config.js";

if (!getToken()) {
  location.href = "./login.html";
}

const modules = [
  {
    key: "profiles",
    label: "个人信息",
    endpoint: "/api/admin/profiles",
    columns: ["id", "fullName", "title", "isActive", "updatedAt"],
    fields: [
      { key: "fullName", label: "姓名", type: "text", required: true },
      { key: "title", label: "职位标题", type: "text", required: true },
      { key: "avatarUrl", label: "头像 URL", type: "url", full: true },
      { key: "location", label: "所在地", type: "text" },
      { key: "resumePdfUrl", label: "简历 PDF URL", type: "url", full: true },
      { key: "bio", label: "个人简介", type: "textarea", full: true },
      { key: "isActive", label: "设为前台展示", type: "checkbox" }
    ]
  },
  {
    key: "skills",
    label: "技能",
    endpoint: "/api/admin/skills",
    columns: ["id", "profileId", "name", "sortOrder", "isVisible"],
    fields: [
      { key: "profileId", label: "档案 ID", type: "number", min: 1 },
      { key: "name", label: "技能名称", type: "text", required: true },
      { key: "sortOrder", label: "排序值", type: "number", min: 0 },
      { key: "isVisible", label: "是否显示", type: "checkbox" }
    ]
  },
  {
    key: "projects",
    label: "项目经历",
    endpoint: "/api/admin/projects",
    columns: ["id", "profileId", "name", "role", "startDate", "endDate", "sortOrder", "isVisible"],
    fields: [
      { key: "profileId", label: "档案 ID", type: "number", min: 1 },
      { key: "name", label: "项目名称", type: "text", required: true, full: true },
      { key: "role", label: "角色", type: "text", required: true },
      { key: "startDate", label: "开始日期", type: "date", required: true },
      { key: "endDate", label: "结束日期", type: "date" },
      { key: "demoUrl", label: "演示链接", type: "url", full: true },
      { key: "repoUrl", label: "仓库链接", type: "url", full: true },
      { key: "summary", label: "项目简介", type: "textarea", full: true },
      { key: "highlight", label: "项目亮点", type: "textarea", full: true },
      { key: "sortOrder", label: "排序值", type: "number", min: 0 },
      { key: "isVisible", label: "是否显示", type: "checkbox" }
    ]
  },
  {
    key: "work-experiences",
    label: "工作/实习经历",
    endpoint: "/api/admin/work-experiences",
    columns: ["id", "profileId", "company", "position", "type", "startDate", "endDate", "sortOrder", "isVisible"],
    fields: [
      { key: "profileId", label: "档案 ID", type: "number", min: 1 },
      { key: "company", label: "公司", type: "text", required: true },
      { key: "position", label: "岗位", type: "text", required: true },
      { key: "type", label: "类型", type: "text", required: true },
      { key: "startDate", label: "开始日期", type: "date", required: true },
      { key: "endDate", label: "结束日期", type: "date" },
      { key: "description", label: "工作描述", type: "textarea", full: true },
      { key: "sortOrder", label: "排序值", type: "number", min: 0 },
      { key: "isVisible", label: "是否显示", type: "checkbox" }
    ]
  },
  {
    key: "educations",
    label: "教育经历",
    endpoint: "/api/admin/educations",
    columns: ["id", "profileId", "school", "degree", "major", "startDate", "endDate", "sortOrder", "isVisible"],
    fields: [
      { key: "profileId", label: "档案 ID", type: "number", min: 1 },
      { key: "school", label: "学校", type: "text", required: true },
      { key: "degree", label: "学历", type: "text", required: true },
      { key: "major", label: "专业", type: "text", required: true },
      { key: "startDate", label: "开始日期", type: "date", required: true },
      { key: "endDate", label: "结束日期", type: "date" },
      { key: "gpa", label: "GPA", type: "text" },
      { key: "sortOrder", label: "排序值", type: "number", min: 0 },
      { key: "isVisible", label: "是否显示", type: "checkbox" }
    ]
  },
  {
    key: "honors",
    label: "荣誉",
    endpoint: "/api/admin/honors",
    columns: ["id", "profileId", "title", "issuer", "awardDate", "sortOrder", "isVisible"],
    fields: [
      { key: "profileId", label: "档案 ID", type: "number", min: 1 },
      { key: "title", label: "荣誉名称", type: "text", required: true, full: true },
      { key: "issuer", label: "颁发方", type: "text" },
      { key: "awardDate", label: "获奖日期", type: "date" },
      { key: "description", label: "说明", type: "textarea", full: true },
      { key: "sortOrder", label: "排序值", type: "number", min: 0 },
      { key: "isVisible", label: "是否显示", type: "checkbox" }
    ]
  },
  {
    key: "contacts",
    label: "联系方式",
    endpoint: "/api/admin/contacts",
    columns: ["id", "profileId", "type", "label", "value", "sortOrder", "isVisible"],
    fields: [
      { key: "profileId", label: "档案 ID", type: "number", min: 1 },
      { key: "type", label: "类型", type: "text", required: true },
      { key: "label", label: "名称", type: "text", required: true },
      { key: "value", label: "内容", type: "text", required: true, full: true },
      { key: "sortOrder", label: "排序值", type: "number", min: 0 },
      { key: "isVisible", label: "是否显示", type: "checkbox" }
    ]
  }
];

const columnLabelMap = {
  id: "ID",
  profileId: "档案ID",
  fullName: "姓名",
  title: "职位标题",
  isActive: "前台展示",
  updatedAt: "更新时间",
  name: "名称",
  sortOrder: "排序值",
  isVisible: "是否显示",
  role: "角色",
  startDate: "开始日期",
  endDate: "结束日期",
  company: "公司",
  position: "岗位",
  type: "类型",
  school: "学校",
  degree: "学历",
  major: "专业",
  label: "名称",
  value: "内容",
  issuer: "颁发方",
  awardDate: "获奖日期"
};

const moduleNav = document.getElementById("moduleNav");
const moduleTitle = document.getElementById("moduleTitle");
const tableHead = document.getElementById("tableHead");
const tableBody = document.getElementById("tableBody");
const entityForm = document.getElementById("entityForm");
const formTitle = document.getElementById("formTitle");
const toast = document.getElementById("toast");

const state = {
  active: modules[0],
  rows: [],
  editingId: null,
  defaultProfileId: null
};

function showToast(text, isError = false) {
  toast.textContent = text;
  toast.style.color = isError ? "#b23a2f" : "#0f6e79";
}

function formatValue(value) {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? "是" : "否";
  const str = String(value);
  return str.length > 60 ? `${str.slice(0, 60)}...` : str;
}

function renderNav() {
  moduleNav.innerHTML = modules.map((m) => `
    <button data-key="${m.key}" class="${state.active.key === m.key ? "active" : ""}">${m.label}</button>
  `).join("");

  moduleNav.querySelectorAll("button").forEach((btn) => {
    btn.addEventListener("click", () => {
      const found = modules.find((m) => m.key === btn.dataset.key);
      if (!found) return;
      state.active = found;
      state.editingId = null;
      renderNav();
      renderForm();
      loadData();
    });
  });
}

function renderTable() {
  moduleTitle.textContent = state.active.label;
  tableHead.innerHTML = `<tr>${state.active.columns.map((c) => `<th>${columnLabelMap[c] || c}</th>`).join("")}<th>操作</th></tr>`;

  tableBody.innerHTML = state.rows.map((row) => `
    <tr>
      ${state.active.columns.map((c) => `<td>${formatValue(row[c])}</td>`).join("")}
      <td>
        <div class="actions">
          <button data-action="edit" data-id="${row.id}">编辑</button>
          <button data-action="delete" data-id="${row.id}" class="danger">删除</button>
        </div>
      </td>
    </tr>
  `).join("");

  tableBody.querySelectorAll("button").forEach((btn) => {
    const id = Number(btn.dataset.id);
    btn.addEventListener("click", () => {
      if (btn.dataset.action === "edit") {
        const current = state.rows.find((r) => r.id === id);
        if (!current) return;
        state.editingId = id;
        renderForm(current);
      } else if (btn.dataset.action === "delete") {
        handleDelete(id);
      }
    });
  });
}

function fieldToInput(field, value) {
  const fullClass = field.full ? "full" : "";

  if (field.type === "textarea") {
    return `
      <label class="${fullClass}">
        ${field.label}
        <textarea name="${field.key}" rows="4">${value || ""}</textarea>
      </label>
    `;
  }

  if (field.type === "checkbox") {
    return `
      <label class="${fullClass} inline-row">
        <input type="checkbox" name="${field.key}" ${value === false ? "" : "checked"} />
        ${field.label}
      </label>
    `;
  }

  const min = field.min != null ? `min="${field.min}"` : "";
  const max = field.max != null ? `max="${field.max}"` : "";
  const required = field.required ? "required" : "";
  const currentValue = value == null ? "" : value;

  return `
      <label class="${fullClass}">
        ${field.label}
        <input type="${field.type || "text"}" name="${field.key}" value="${currentValue}" ${min} ${max} ${required} />
      </label>
    `;
}

function getDefaultFieldValue(field, row) {
  if (row) {
    return row[field.key];
  }
  if (field.key === "profileId" && state.defaultProfileId != null) {
    return state.defaultProfileId;
  }
  return null;
}

function renderForm(row = null) {
  state.editingId = row?.id || null;
  formTitle.textContent = state.editingId ? `编辑 ID=${state.editingId}` : "新增";

  const hiddenId = state.editingId ? `<input type="hidden" name="id" value="${state.editingId}" />` : "";

  entityForm.innerHTML = `
    ${hiddenId}
    ${state.active.fields.map((field) => fieldToInput(field, getDefaultFieldValue(field, row))).join("")}
    <div class="full inline-row">
      <button type="submit">${state.editingId ? "保存" : "创建"}</button>
      <button type="button" id="resetFormBtn" class="ghost">重置</button>
    </div>
  `;

  document.getElementById("resetFormBtn").addEventListener("click", () => {
    state.editingId = null;
    renderForm();
  });
}

function collectPayload(form) {
  const payload = {};

  state.active.fields.forEach((field) => {
    const el = form.elements[field.key];
    if (!el) return;

    let value;
    if (field.type === "checkbox") {
      value = el.checked;
    } else {
      value = el.value?.trim();
    }

    if (field.type === "number") {
      value = value === "" ? null : Number(value);
    }

    if (field.type !== "checkbox" && value === "") {
      value = null;
    }

    payload[field.key] = value;
  });

  return payload;
}

async function loadDefaultProfileId() {
  try {
    const profiles = await request("/api/admin/profiles", {}, true);
    if (!Array.isArray(profiles) || profiles.length === 0) {
      state.defaultProfileId = null;
      return;
    }
    const active = profiles.find((item) => item.isActive === true);
    state.defaultProfileId = (active || profiles[0]).id;
  } catch (error) {
    state.defaultProfileId = null;
  }
}

async function loadData() {
  showToast("加载中...");
  try {
    const data = await request(state.active.endpoint, {}, true);
    state.rows = Array.isArray(data) ? data : [];
    renderTable();
    showToast(`已加载 ${state.rows.length} 条数据`);
  } catch (error) {
    showToast(error.message, true);
  }
}

async function handleDelete(id) {
  if (!confirm(`确定删除 ID=${id} 吗？`)) return;

  try {
    await request(`${state.active.endpoint}/${id}`, { method: "DELETE" }, true);
    if (state.editingId === id) {
      state.editingId = null;
      renderForm();
    }
    await loadData();
    showToast("删除成功");
  } catch (error) {
    showToast(error.message, true);
  }
}

entityForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const payload = collectPayload(entityForm);
  const isEdit = Boolean(state.editingId);

  try {
    await request(
      isEdit ? `${state.active.endpoint}/${state.editingId}` : state.active.endpoint,
      {
        method: isEdit ? "PUT" : "POST",
        body: JSON.stringify(payload)
      },
      true
    );

    state.editingId = null;
    renderForm();
    await loadData();
    showToast(isEdit ? "更新成功" : "创建成功");
  } catch (error) {
    showToast(error.message, true);
  }
});

document.getElementById("logoutBtn").addEventListener("click", () => {
  clearToken();
  location.href = "./login.html";
});

document.getElementById("apiBaseBtn").addEventListener("click", () => {
  const current = getApiBase();
  const value = prompt("请输入后端 API 地址", current);
  if (!value) return;
  setApiBase(value.trim());
  showToast(`API 地址已更新：${value.trim()}`);
  loadData();
});

(async function init() {
  await loadDefaultProfileId();
  renderNav();
  renderForm();
  await loadData();
})();