import { useEffect, useMemo, useState } from "react";

function resolveQuality({ width, reducedMotion, cores }) {
  if (reducedMotion || width < 768) {
    return "low";
  }

  if (cores && cores <= 4) {
    return "medium";
  }

  return "high";
}

export function usePerfGuard() {
  const [viewportWidth, setViewportWidth] = useState(() => window.innerWidth);
  const [reducedMotion, setReducedMotion] = useState(() =>
    window.matchMedia("(prefers-reduced-motion: reduce)").matches
  );

  useEffect(() => {
    const media = window.matchMedia("(prefers-reduced-motion: reduce)");

    const onResize = () => {
      setViewportWidth(window.innerWidth);
    };

    const onMotionChange = (event) => {
      setReducedMotion(event.matches);
    };

    window.addEventListener("resize", onResize, { passive: true });
    media.addEventListener("change", onMotionChange);

    return () => {
      window.removeEventListener("resize", onResize);
      media.removeEventListener("change", onMotionChange);
    };
  }, []);

  return useMemo(() => {
    const cores = navigator.hardwareConcurrency || 4;
    const sceneQuality = resolveQuality({
      width: viewportWidth,
      reducedMotion,
      cores
    });

    return {
      viewportWidth,
      reducedMotion,
      isMobile: viewportWidth < 900,
      sceneQuality
    };
  }, [viewportWidth, reducedMotion]);
}
