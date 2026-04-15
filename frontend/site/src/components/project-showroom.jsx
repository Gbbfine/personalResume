import { useEffect, useLayoutEffect, useMemo, useRef, useState } from "react";
import gsap from "gsap";
import { ArrowUpRight } from "lucide-react";
import { formatDateRange, safe } from "../lib/format";

const mediaPalette = [
  "linear-gradient(130deg, rgba(61,214,255,0.35), rgba(124,140,255,0.3), rgba(255,107,61,0.16))",
  "linear-gradient(145deg, rgba(124,140,255,0.42), rgba(61,214,255,0.2), rgba(11,18,32,0.86))",
  "linear-gradient(150deg, rgba(255,107,61,0.26), rgba(61,214,255,0.24), rgba(124,140,255,0.34))"
];

function multilineText(value) {
  return safe(value, "").replace(/\\n/g, "\n");
}

function ProjectLinks({ item }) {
  return (
    <div className="showroom-links">
      {item.demoUrl ? (
        <a href={item.demoUrl} target="_blank" rel="noreferrer">
          Live Demo <ArrowUpRight size={14} />
        </a>
      ) : null}
      {item.repoUrl ? (
        <a href={item.repoUrl} target="_blank" rel="noreferrer">
          Source <ArrowUpRight size={14} />
        </a>
      ) : null}
    </div>
  );
}

export function ProjectShowroom({ projects, isMobile, reducedMotion }) {
  const items = useMemo(() => (projects || []).slice(0, 6), [projects]);
  const [activeIndex, setActiveIndex] = useState(0);
  const rootRef = useRef(null);
  const mediaRef = useRef(null);

  useLayoutEffect(() => {
    if (isMobile || reducedMotion || items.length <= 1 || !rootRef.current) {
      return undefined;
    }

    const ctx = gsap.context(() => {
      gsap.from(".showroom-rail-item", {
        opacity: 0,
        x: -18,
        duration: 0.55,
        stagger: 0.08,
        ease: "power3.out",
        scrollTrigger: {
          trigger: rootRef.current,
          start: "top 78%"
        }
      });
    }, rootRef);

    return () => ctx.revert();
  }, [isMobile, reducedMotion, items.length]);

  useEffect(() => {
    if (!mediaRef.current) {
      return;
    }

    gsap.fromTo(
      mediaRef.current,
      { opacity: 0.35, y: 10, scale: 0.97 },
      { opacity: 1, y: 0, scale: 1, duration: 0.52, ease: "power3.out", clearProps: "transform" }
    );
  }, [activeIndex]);

  if (!items.length) {
    return null;
  }

  if (isMobile || reducedMotion) {
    return (
      <div className="showroom-mobile" data-testid="showroom-mobile">
        {items.map((item, idx) => (
          <article key={`${item.name}-${idx}`} className="showroom-mobile-card">
            <div className="showroom-mobile-bg" style={{ background: mediaPalette[idx % mediaPalette.length] }} />
            <div className="showroom-mobile-content">
              <p>{safe(item.role)}</p>
              <h3>{safe(item.name)}</h3>
              <span>{formatDateRange(item.startDate, item.endDate)}</span>
              <p className="showroom-mobile-summary">{multilineText(item.summary)}</p>
              <p className="showroom-mobile-highlight">{multilineText(item.highlight)}</p>
              <ProjectLinks item={item} />
            </div>
          </article>
        ))}
      </div>
    );
  }

  const active = items[activeIndex];

  return (
    <div ref={rootRef} className="showroom-shell" data-testid="project-showroom">
      <aside className="showroom-rail">
        {items.map((item, idx) => (
          <button
            key={`${item.name}-${idx}`}
            className={`showroom-rail-item ${idx === activeIndex ? "is-active" : ""}`}
            onClick={() => setActiveIndex(idx)}
            type="button"
          >
            <span>{String(idx + 1).padStart(2, "0")}</span>
            <div>
              <strong>{safe(item.name)}</strong>
              <small>{safe(item.role)}</small>
            </div>
          </button>
        ))}
      </aside>

      <article className="showroom-stage" ref={mediaRef} data-testid="showroom-active-card">
        <div className="showroom-stage-bg" style={{ background: mediaPalette[activeIndex % mediaPalette.length] }} />
        <div className="showroom-stage-layer showroom-stage-layer-glow" />
        <div className="showroom-stage-layer showroom-stage-layer-grid" />

        <div className="showroom-stage-content">
          <header className="showroom-stage-panel showroom-stage-panel-meta">
            <p className="showroom-stage-role">{safe(active.role)}</p>
            <p className="showroom-stage-range">{formatDateRange(active.startDate, active.endDate)}</p>
          </header>

          <section className="showroom-stage-panel showroom-stage-panel-main">
            <h3>{safe(active.name)}</h3>
            <p className="showroom-stage-summary">{multilineText(active.summary)}</p>
          </section>

          <footer className="showroom-stage-panel showroom-stage-panel-foot">
            <p className="showroom-stage-highlight">{multilineText(active.highlight)}</p>
            <ProjectLinks item={active} />
          </footer>
        </div>
      </article>
    </div>
  );
}
