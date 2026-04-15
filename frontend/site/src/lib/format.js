export function safe(value, fallback = "-") {
  return value == null || value === "" ? fallback : value;
}

export function formatDateRange(startDate, endDate) {
  if (!startDate) {
    return "";
  }
  return `${startDate} ~ ${endDate || "至今"}`;
}

export function contactHref(contact) {
  const value = String(contact?.value || "").trim();
  const type = String(contact?.type || "").toLowerCase();

  if (!value) {
    return "";
  }

  if (["email", "phone", "tel", "mobile"].includes(type)) {
    return "";
  }

  if (value.startsWith("http://") || value.startsWith("https://")) {
    return value;
  }

  return `https://${value}`;
}

function parseDateToTs(value) {
  if (!value) {
    return 0;
  }
  const ts = Date.parse(value);
  return Number.isNaN(ts) ? 0 : ts;
}

export function toTimelineItems(data) {
  const nowTs = Date.now();

  const work = (data.workExperiences || []).map((item) => ({
    kind: "Work",
    title: `${safe(item.company)} · ${safe(item.position)}`,
    subTitle: safe(item.type, "") || "",
    range: formatDateRange(item.startDate, item.endDate),
    description: safe(item.description, ""),
    sortTs: item.endDate ? parseDateToTs(item.endDate) : nowTs
  }));

  const honors = (data.honors || []).map((item) => ({
    kind: "Honor",
    title: safe(item.title),
    subTitle: safe(item.issuer, ""),
    range: safe(item.awardDate, ""),
    description: safe(item.description, ""),
    sortTs: parseDateToTs(item.awardDate)
  }));

  return [...work, ...honors]
    .filter((item) => item.title && item.title !== "-")
    .sort((a, b) => b.sortTs - a.sortTs)
    .map(({ sortTs, ...rest }) => rest);
}

export function toHeroHighlights(skills) {
  const fallback = ["Backend Systems", "Product Frontend", "Motion Architecture"];
  const names = (skills || []).map((item) => safe(item.name)).filter((name) => name && name !== "-");

  if (names.length >= 3) {
    return [
      `${names[0]} + ${names[1]}`,
      `${names[2]} + ${names[3] || "Engineering"}`,
      `${names[4] || "GSAP"} + ${names[5] || "Three.js"}`
    ];
  }

  return fallback;
}
