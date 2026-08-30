# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.1](https://github.com/xRahul/Java---Color-Picker/compare/v2.0.0...v2.0.1) (2026-08-30)


### Bug Fixes

* make publish-release trigger on tag push to avoid GITHUB_TOKEN loop ([1b1e59b](https://github.com/xRahul/Java---Color-Picker/commit/1b1e59babcea6c01a01ea5262204ef585081b588))

## [2.0.0](https://github.com/xRahul/Java---Color-Picker/compare/v1.0.0...v2.0.0) (2026-08-30)


### ⚠ BREAKING CHANGES

* none - just forces releasable commit for release-please.

### Features

* trigger initial release-please check ([9d4598e](https://github.com/xRahul/Java---Color-Picker/commit/9d4598edd63d3fc5241252e792e938b0bf99b943))
* verify release-please pipeline creates release PR ([77cc990](https://github.com/xRahul/Java---Color-Picker/commit/77cc99075ebaa7effe6bb7cc29ba6b4e1a6a461f))


### Bug Fixes

* add release-please manifest for java release-type ([5790d65](https://github.com/xRahul/Java---Color-Picker/commit/5790d65dde7d0fe341c1fa384d541ea9cdeb4c10))
* configure release-please with manifest and simple release-type ([bd4367e](https://github.com/xRahul/Java---Color-Picker/commit/bd4367e1cd3ea6470cec89cdfffb7284f71aaebf))

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
- Upgrade JUnit Jupiter to 5.12.2
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
