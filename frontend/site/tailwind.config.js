/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  darkMode: ["class"],
  theme: {
    extend: {
      fontFamily: {
        sans: ["'Space Grotesk'", "'Noto Sans SC'", "sans-serif"]
      },
      colors: {
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",
        muted: "hsl(var(--muted))",
        mutedForeground: "hsl(var(--muted-foreground))",
        border: "hsl(var(--border))",
        card: "hsl(var(--card))",
        cardForeground: "hsl(var(--card-foreground))",
        primary: "hsl(var(--primary))",
        primaryForeground: "hsl(var(--primary-foreground))"
      },
      boxShadow: {
        glow: "0 0 0 1px rgba(147, 197, 253, 0.25), 0 24px 60px rgba(8, 19, 56, 0.65)",
        soft: "0 12px 36px rgba(8, 19, 56, 0.45)"
      },
      keyframes: {
        pulseSlow: {
          "0%, 100%": { opacity: "0.55", transform: "scale(1)" },
          "50%": { opacity: "0.9", transform: "scale(1.08)" }
        },
        shimmer: {
          "0%": { transform: "translateX(-120%)" },
          "100%": { transform: "translateX(140%)" }
        }
      },
      animation: {
        pulseSlow: "pulseSlow 6s ease-in-out infinite",
        shimmer: "shimmer 2.8s linear infinite"
      }
    }
  },
  plugins: []
};
