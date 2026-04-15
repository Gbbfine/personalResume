import { motion } from "framer-motion";
import { motionTokens } from "../motion/motion-tokens";

export function CapabilityCluster({ groups }) {
  return (
    <div className="capability-grid">
      {groups.map((group, index) => (
        <motion.article
          key={group.title}
          className="capability-card"
          initial={{ opacity: 0, y: 24 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true, amount: 0.35 }}
          transition={{
            duration: motionTokens.duration.base,
            delay: index * motionTokens.stagger.tight,
            ease: motionTokens.easeOutExpo
          }}
        >
          <p>{group.title}</p>
          <h3>{group.summary}</h3>
          <ul>
            {group.stack.map((item) => (
              <li key={`${group.title}-${item}`}>{item}</li>
            ))}
          </ul>
          <small>代表作品: {group.sample}</small>
        </motion.article>
      ))}
    </div>
  );
}
