import * as React from "react";
import { cva } from "class-variance-authority";
import { cn } from "../../lib/utils";

const buttonVariants = cva(
  "inline-flex items-center justify-center rounded-xl text-sm font-semibold transition-all duration-300 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary/70 disabled:pointer-events-none disabled:opacity-50",
  {
    variants: {
      variant: {
        default:
          "bg-primary text-primary-foreground shadow-glow hover:brightness-110",
        ghost:
          "border border-white/20 bg-white/5 text-white hover:bg-white/10"
      },
      size: {
        default: "h-11 px-5",
        lg: "h-12 px-6",
        icon: "h-11 w-11"
      }
    },
    defaultVariants: {
      variant: "default",
      size: "default"
    }
  }
);

const Button = React.forwardRef(({ className, variant, size, asChild = false, ...props }, ref) => {
  const Comp = asChild ? "span" : "button";
  return (
    <Comp
      className={cn(buttonVariants({ variant, size, className }))}
      ref={ref}
      {...props}
    />
  );
});
Button.displayName = "Button";

export { Button, buttonVariants };
