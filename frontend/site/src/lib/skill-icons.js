const iconMap = {
  java: "java/java-original.svg",
  spring: "spring/spring-original.svg",
  springboot: "spring/spring-original.svg",
  redis: "redis/redis-original.svg",
  mysql: "mysql/mysql-original.svg",
  docker: "docker/docker-original.svg",
  git: "git/git-original.svg",
  html: "html5/html5-original.svg",
  html5: "html5/html5-original.svg",
  css: "css3/css3-original.svg",
  css3: "css3/css3-original.svg",
  javascript: "javascript/javascript-original.svg",
  js: "javascript/javascript-original.svg",
  typescript: "typescript/typescript-original.svg",
  ts: "typescript/typescript-original.svg",
  vue: "vuejs/vuejs-original.svg",
  react: "react/react-original.svg",
  node: "nodejs/nodejs-original.svg",
  nodejs: "nodejs/nodejs-original.svg",
  python: "python/python-original.svg",
  py: "python/python-original.svg",
  nginx: "nginx/nginx-original.svg",
  rabbitmq: "rabbitmq/rabbitmq-original.svg"
};

function normalizeSkillKey(name) {
  return String(name || "")
    .toLowerCase()
    .replace(/\+/g, "plus")
    .replace(/[^a-z0-9]/g, "");
}

export function getSkillIconUrl(name) {
  const key = normalizeSkillKey(name);
  const path = iconMap[key];
  return path ? `https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${path}` : "";
}
