import { motion } from "framer-motion";
import { motionTokens } from "../motion/motion-tokens";

export function SectionFrame({ id, badge, title, description, children, className = "" }) {
  return (
    <section id={id} className={`section-frame reveal-section ${className}`.trim()}>
      <motion.header
        initial={{ opacity: 0, y: 22 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true, amount: 0.3 }}
        transition={{ duration: motionTokens.duration.base, ease: motionTokens.easeOutExpo }}
        className="section-head"
      >
        <p className="section-badge">{badge}</p>
        <h2>{title}</h2>
        {description ? <p>{description}</p> : null}
      </motion.header>
      {children}
    </section>
  );
}
