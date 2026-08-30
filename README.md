# Java Color Picker

[![CI](https://github.com/xRahul/Java---Color-Picker/actions/workflows/ci.yml/badge.svg)](https://github.com/xRahul/Java---Color-Picker/actions/workflows/ci.yml) [![Java 21](https://img.shields.io/badge/Java-21-blue)](https://openjdk.org/projects/jdk/21/) [![Maven](https://img.shields.io/badge/Maven-3.9-red)](https://maven.apache.org/)

A simple Java Swing application to pick and view colors side-by-side with their information.

## Screenshot

![Screenshot](https://raw.githubusercontent.com/xRahul/Java---Color-Picker/master/Screens/Screen2.jpg)

## Features

*   Pick colors using mouse on the main panel (Hue X-axis, Saturation Y-axis).
*   Brightness control via mouse wheel.
*   Click to save current color to panel and view its details (RGB, Hex).
*   macOS full-screen support via `Launcher`.

## Tech Stack

| Layer | Choice |
|-------|--------|
| Language | Java 21 |
| UI | Swing (headless-testable) |
| Build | Maven 3.9, `maven-compiler-plugin` 3.13.0, `exec-maven-plugin` 3.2.0 |
| Test | JUnit Jupiter 5.12.2 (incl. `junit-jupiter-params`), AssertJ 3.27.0, `maven-surefire-plugin` 3.5.3 (`java.awt.headless=true`) |
| Coverage | JaCoCo 0.8.13, `mvn verify` gate 60% LINE BUNDLE (excludes `Launcher`/`ColorPickerFrame` Swing code; `ColorUtils`+`ColorModel` at 100%) |
| CI/CD | GitHub Actions (Temurin 21, cache maven), release-please + publish-release, Dependabot (maven weekly, actions monthly) |

## Project Structure

```
src/main/java/com/colorpicker/
  Launcher.java         # entry point, EDT bootstrap, macOS hooks
  ColorModel.java       # domain model: HSB state (clamped 0..1) + getColor()
  ColorPickerFrame.java # JFrame, delegates HSB to ColorModel
  ColorUtils.java       # pure utils (RGB↔Hex, HSB helpers)
src/test/java/com/colorpicker/
  ColorUtilsTest.java              # 10 tests, @MethodSource 7 colors + edge
  ColorModelTest.java              # 12 tests, clamp + getColor
  ColorPickerFrameHeadlessTest.java# 7 tests, headless-safe (reflection fallback), verifies addColorPanel
```

## Prerequisites

*   JDK 21 (Temurin recommended)
*   Maven 3.9+

## Build and Run

```bash
git clone https://github.com/xRahul/Java---Color-Picker.git
cd Java---Color-Picker
mvn clean package exec:java          # builds and launches on EDT
# or
mvn clean package
java -jar target/color-picker-1.0-SNAPSHOT.jar
```

## Testing and Coverage

```bash
mvn clean verify                     # 29 tests, JaCoCo report + 60% gate

mvn test                             # tests only (no coverage gate)
open target/site/jacoco/index.html   # coverage report
```

*   `maven-surefire-plugin` sets `java.awt.headless=true` so Swing tests pass in CI without a display.
*   `ColorPickerFrameHeadlessTest` branches on `HeadlessException` → reflection fallback to keep CI green.

## CI

*   `.github/workflows/ci.yml` — triggers on `push`/`pull_request` to `master`: `setup-java@v4` (21/temurin, cache maven), `mvn clean verify`, uploads `target/site/jacoco/` as artifact (`if: always()`).
*   `.github/dependabot.yml` — maven weekly, github-actions monthly.

## Release Pipeline

Conventional Commits → automated GitHub Releases JAR (no local version bumping):

*   `.release-please-config.json` — `release-type: java`, `changelog-types` for feat/fix/docs/style/refactor/test/chore/build.
*   `.github/workflows/release-please.yml` — on `push` to `master`, `googleapis/release-please-action@v4` (config-file) with `contents:write`/`pull-requests:write`, creates/updates a Release PR and tags.
*   `.github/workflows/publish-release.yml` — on `release: types: [published]`, checks out, `setup-java@v4` 21, `mvn clean package -DskipTests`, `softprops/action-gh-release@v2` uploads `target/color-picker-*.jar` to the release.

**Authoring commits:**

```
feat: add saturation slider
fix: clamp brightness on wheel overflow
docs: update README for Java 21
```

See `CHANGELOG.md` (Keep a Changelog + SemVer; `[Unreleased]` tracks the current modernization batch).

## Development

Source is in `src/main/java/com/colorpicker` as above. Key conventions:

*   `ColorModel` is the only mutable HSB holder; `ColorPickerFrame` delegates to it (`updateColor`, `mouseWheelMoved`, `mouseClicked`).
*   `ColorUtils` stays pure/static for easy param testing.

## Changelog

See [CHANGELOG.md](CHANGELOG.md).

## License

MIT (see `LICENSE` if present; otherwise treat as MIT per GitHub default).
