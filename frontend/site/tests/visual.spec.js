import { test } from "@playwright/test";
import path from "path";

const shot = (name) => path.join("test-results", "visual", name);

test.describe("desktop visual", () => {
  test("hero + showroom + fullpage", async ({ page }) => {
    await page.setViewportSize({ width: 1440, height: 900 });
    await page.goto("/", { waitUntil: "domcontentloaded" });
    await page.waitForTimeout(1800);

    await page.locator('[data-testid="hero-stage"]').screenshot({
      path: shot("desktop-hero.png")
    });

    await page.locator("#selected-work").scrollIntoViewIfNeeded();
    await page.waitForTimeout(850);

    await page.locator('[data-testid="project-showroom"]').screenshot({
      path: shot("desktop-showroom.png")
    });

    await page.locator("#tech-stack").scrollIntoViewIfNeeded();
    await page.waitForTimeout(600);

    await page.locator('[data-testid="tech-stack-grid"]').screenshot({
      path: shot("desktop-tech-stack.png")
    });

    await page.locator("#timeline").scrollIntoViewIfNeeded();
    await page.waitForTimeout(650);

    await page.locator('[data-testid="timeline-rail"]').screenshot({
      path: shot("desktop-timeline.png")
    });

    await page.locator('[data-testid="showroom-active-card"]').hover();
    await page.waitForTimeout(260);

    await page.screenshot({
      path: shot("desktop-full.png"),
      fullPage: true
    });
  });
});

test.describe("mobile degrade visual", () => {
  test("mobile layout should fallback to card flow", async ({ page }) => {
    await page.setViewportSize({ width: 390, height: 844 });
    await page.goto("/", { waitUntil: "domcontentloaded" });
    await page.waitForTimeout(1400);

    await page.locator('[data-testid="hero-stage"]').screenshot({
      path: shot("mobile-hero.png")
    });

    await page.locator('[data-testid="showroom-mobile"]').screenshot({
      path: shot("mobile-showroom.png")
    });

    await page.screenshot({
      path: shot("mobile-full.png"),
      fullPage: true
    });
  });
});
