import { motion } from "framer-motion";

export function SectionHeading({ badge, title, description }) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 22 }}
      whileInView={{ opacity: 1, y: 0 }}
      viewport={{ once: true, amount: 0.3 }}
      transition={{ duration: 0.5, ease: [0.16, 1, 0.3, 1] }}
      className="mb-6"
    >
      <p className="mb-2 inline-flex rounded-full border border-sky-300/35 bg-sky-400/10 px-3 py-1 text-xs font-semibold tracking-[0.18em] text-sky-100/90 uppercase">
        {badge}
      </p>
      <h2 className="text-3xl font-bold tracking-tight text-white">{title}</h2>
      {description ? <p className="mt-2 max-w-3xl text-sm text-slate-300/90">{description}</p> : null}
    </motion.div>
  );
}
