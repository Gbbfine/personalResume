import { motion } from "framer-motion";

export function MethodCard({ items }) {
  return (
    <div className="method-grid">
      {items.map((item, index) => (
        <motion.article
          key={item.title}
          className="method-card"
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true, amount: 0.35 }}
          transition={{ duration: 0.45, delay: index * 0.06, ease: [0.16, 1, 0.3, 1] }}
        >
          <p>{item.title}</p>
          <h3>{item.detail}</h3>
        </motion.article>
      ))}
    </div>
  );
}
