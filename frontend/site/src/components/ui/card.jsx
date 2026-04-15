import { cn } from "../../lib/utils";

export function Card({ className, children, ...props }) {
  return (
    <article
      className={cn(
        "rounded-2xl border border-white/10 bg-white/5 p-6 text-card-foreground backdrop-blur-xl",
        className
      )}
      {...props}
    >
      {children}
    </article>
  );
}
