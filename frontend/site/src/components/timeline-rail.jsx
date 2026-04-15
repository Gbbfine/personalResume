import { motion } from "framer-motion";
import { safe } from "../lib/format";

function readYear(range) {
  const value = String(range || "");
  const match = value.match(/\d{4}/);
  return match ? match[0] : "----";
}

export function TimelineRail({ items }) {
  return (
    <div className="timeline-rail" data-testid="timeline-rail">
      {items.map((item, index) => {
        const isLeft = index % 2 === 0;

        return (
          <motion.article
            key={`${item.kind}-${item.title}-${index}`}
            className={`timeline-row ${isLeft ? "is-left" : "is-right"}`}
            initial={{ opacity: 0, x: isLeft ? -28 : 28, y: 14 }}
            whileInView={{ opacity: 1, x: 0, y: 0 }}
            viewport={{ once: true, amount: 0.3 }}
            transition={{ duration: 0.56, ease: [0.16, 1, 0.3, 1] }}
          >
            <article className={`timeline-card ${isLeft ? "on-left" : "on-right"}`}>
              <header className="timeline-card-head">
                <span className="timeline-kind">{safe(item.kind, "Item")}</span>
                <small className="timeline-range">{safe(item.range, "")}</small>
              </header>
              <h3>{safe(item.title)}</h3>
              {item.subTitle ? <p className="timeline-subtitle">{item.subTitle}</p> : null}
              {item.description ? <p className="timeline-description">{item.description}</p> : null}
            </article>

            <div className="timeline-center" aria-hidden="true">
              <span className="timeline-year">{readYear(item.range)}</span>
              <span className="timeline-node" />
            </div>
          </motion.article>
        );
      })}
    </div>
  );
}

