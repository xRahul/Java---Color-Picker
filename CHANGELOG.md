# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- `ColorModel` domain model extracting HSB state (hue/saturation/brightness) with clamped setters and `getColor()` for testability
- Comprehensive `ColorModelTest` (12 tests) covering defaults, clamping, and color computation
- Headless Swing tests `ColorPickerFrameHeadlessTest` (7 tests) including `addColorPanel` verification
- Parameterized `ColorUtilsTest` with `@MethodSource` (7 color cases) and AssertJ assertions
- JaCoCo 0.8.13 coverage gate (60% LINE, BUNDLE) with `prepare-agent` / `report` / `check` executions and report upload in CI
- AssertJ 3.27.0 for fluent test assertions
- CI JaCoCo report artifact upload (`target/site/jacoco/`) with `if: always()`
- release-please automation (`.release-please-config.json` + `release-please.yml` + `publish-release.yml`) for GitHub Releases JAR publishing
- Dependabot weekly (Maven) and monthly (GitHub Actions) updates

### Changed
- Upgrade Java source/target from 8 to 21
- Upgrade JUnit Jupiter to 5.12.3
- Upgrade Maven plugins: `maven-compiler-plugin` 3.13.0, `maven-surefire-plugin` 3.5.3, `exec-maven-plugin` 3.2.0, `versions-maven-plugin` 2.18.0
- Configure `maven-surefire-plugin` with `java.awt.headless=true` for headless test execution
- CI workflow `ci.yml` upgraded to JDK 21 (Temurin) with Maven cache and `mvn clean verify`
- Release workflow split into release-please PR automation and `publish-release.yml` (`release: types: [published]`, `softprops/action-gh-release@v2`, `mvn clean package -DskipTests`, `target/color-picker-*.jar`)
- `ColorPickerFrame` refactored to delegate HSB state to `ColorModel`, add `@Serial` to `serialVersionUID`, remove stale commented block in `mouseWheelMoved`
- `Launcher` removed deprecated macOS `apple.*` system property setters (kept `isMacOSX()`)

### Removed
- `PrototypePanels.java` unused dead code
- Manual `release.yml` (`workflow_dispatch` with `versions:set`) replaced by release-please split workflows

## [1.0.0] - Unreleased

- Initial modernized baseline tracked under `[Unreleased]` above.
