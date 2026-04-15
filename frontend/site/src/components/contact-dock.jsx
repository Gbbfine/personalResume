import { motion } from "framer-motion";
import { ArrowUpRight } from "lucide-react";
import { contactHref, safe } from "../lib/format";

export function ContactDock({ contacts }) {
  return (
    <div className="contact-dock" data-testid="contact-dock">
      {(contacts || []).map((item, index) => {
        const href = contactHref(item);
        return (
          <motion.article
            key={`${item.label}-${index}`}
            className="contact-item"
            initial={{ opacity: 0, y: 24 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, amount: 0.35 }}
            transition={{ duration: 0.42, delay: index * 0.06, ease: [0.16, 1, 0.3, 1] }}
          >
            <p>{safe(item.type).toUpperCase()}</p>
            <h3>{safe(item.label)}</h3>
            {href ? (
              <a href={href} target="_blank" rel="noreferrer">
                {safe(item.value)}
                <ArrowUpRight size={14} />
              </a>
            ) : (
              <span>{safe(item.value)}</span>
            )}
          </motion.article>
        );
      })}
    </div>
  );
}
