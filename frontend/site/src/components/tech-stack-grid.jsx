import { motion } from "framer-motion";
import { getSkillIconUrl } from "../lib/skill-icons";
import { safe } from "../lib/format";

export function TechStackGrid({ skills }) {
  const items = (skills || []).slice(0, 18);

  return (
    <div className="tech-grid" data-testid="tech-stack-grid">
      {items.map((item, idx) => {
        const name = safe(item.name, "Tech");
        const icon = getSkillIconUrl(name);

        return (
          <motion.article
            key={`${name}-${idx}`}
            className="tech-card"
            initial={{ opacity: 0, y: 18 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, amount: 0.35 }}
            transition={{ duration: 0.38, delay: idx * 0.03, ease: [0.16, 1, 0.3, 1] }}
          >
            <span className="tech-icon-wrap">
              {icon ? (
                <img src={icon} alt={name} loading="lazy" className="tech-icon" />
              ) : (
                <b>{String(name).slice(0, 1).toUpperCase()}</b>
              )}
            </span>
            <p>{name}</p>
          </motion.article>
        );
      })}
    </div>
  );
}
