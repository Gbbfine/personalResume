import { useEffect, useRef, useState } from "react";
import { Sparkles } from "lucide-react";

function readTargetRect(target, rootRect) {
  const rect = target.getBoundingClientRect();
  return {
    left: rect.left - rootRect.left,
    width: rect.width
  };
}

export function NavMagnet({ items }) {
  const rootRef = useRef(null);
  const [indicator, setIndicator] = useState({ left: 0, width: 0, opacity: 0 });

  useEffect(() => {
    const first = rootRef.current?.querySelector("a[data-nav-item='true']");
    if (!first || !rootRef.current) {
      return;
    }

    const rootRect = rootRef.current.getBoundingClientRect();
    const next = readTargetRect(first, rootRect);
    setIndicator({ ...next, opacity: 1 });
  }, []);

  const onEnter = (event) => {
    if (!rootRef.current) {
      return;
    }
    const rootRect = rootRef.current.getBoundingClientRect();
    const next = readTargetRect(event.currentTarget, rootRect);
    setIndicator({ ...next, opacity: 1 });
  };

  const onLeave = () => {
    setIndicator((prev) => ({ ...prev, opacity: 0.45 }));
  };

  return (
    <header className="site-header">
      <div className="container shell nav-shell">
        <a href="#top" className="brand-mark" aria-label="Homepage">
          <Sparkles size={15} />
          <span>Dev Architect</span>
        </a>

        <nav ref={rootRef} className="nav-magnet" onMouseLeave={onLeave}>
          <span
            className="nav-indicator"
            style={{
              width: `${indicator.width}px`,
              transform: `translateX(${indicator.left}px)`,
              opacity: indicator.opacity
            }}
          />
          {items.map((item) => (
            <a
              key={item.href}
              href={item.href}
              data-nav-item="true"
              onMouseEnter={onEnter}
            >
              {item.label}
            </a>
          ))}
        </nav>
      </div>
    </header>
  );
}
