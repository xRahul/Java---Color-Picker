# Java Color Picker Modernization Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Modernize Java Color Picker to Java 21, latest Maven plugins, JaCoCo coverage gate, AssertJ tests, headless Swing tests, release-please automation.

**Architecture:** Maven build with JaCoCo 60% gate, JUnit 5 + AssertJ, extracted ColorModel for testability, CI (JDK21 + verify + JaCoCo upload), release-please PR bot + separate publish-release workflow for JAR, Dependabot weekly.

**Tech Stack:** Java 21, Maven 3.9+, JUnit Jupiter 5.12.2, AssertJ 3.27.0, JaCoCo 0.8.13, maven-compiler 3.13.0, maven-surefire 3.5.3, exec 3.2.0, versions 2.18.0, release-please 4.x

**Spec:** This plan serves as spec.

## Global Constraints

- Java source/target: **21**
- Keep Java, not Kotlin
- Plugins: compiler 3.13.0, surefire 3.5.3, exec 3.2.0, versions 2.18.0
- JaCoCo 0.8.13 with **60% LINE minimum** (BUNDLE)
- JUnit Jupiter **5.12.2** (stable), AssertJ 3.27.0
- Surefire must set `java.awt.headless=true` via systemPropertyVariables
- GitHub Releases only, no Maven Central
- No comments in code unless requested
- Surgical changes only

---

## File Structure

### Files to Create
- `src/main/java/com/colorpicker/ColorModel.java`
- `src/test/java/com/colorpicker/ColorModelTest.java`
- `src/test/java/com/colorpicker/ColorPickerFrameHeadlessTest.java`
- `.github/workflows/release-please.yml`
- `.github/workflows/publish-release.yml`
- `.release-please-config.json`
- `.github/dependabot.yml`
- `CHANGELOG.md`

### Files to Modify
- `pom.xml`
- `src/main/java/com/colorpicker/ColorPickerFrame.java`
- `src/main/java/com/colorpicker/Launcher.java`
- `src/test/java/com/colorpicker/ColorUtilsTest.java`
- `.github/workflows/ci.yml`
- `README.md`

### Files to Delete
- `src/main/java/com/colorpicker/PrototypePanels.java`
- `.github/workflows/release.yml` (old manual dispatch)

---

## Task 1: Remove Dead Code

**Files:**
- Delete: `src/main/java/com/colorpicker/PrototypePanels.java`

- [ ] **Step 1: Verify not referenced**
Run: `grep -r "PrototypePanels" src/` Expected: only its own file
- [ ] **Step 2: Delete file** `rm src/main/java/com/colorpicker/PrototypePanels.java`
- [ ] **Step 3: Verify compile** `mvn compile` -> BUILD SUCCESS
- [ ] **Step 4: Commit** `git add -A && git commit -m "chore: remove unused PrototypePanels class"`

---

## Task 2: Modernize Launcher.java

**Files:**
- Modify: `src/main/java/com/colorpicker/Launcher.java`

- [ ] **Step 1: Remove deprecated macOS setters** Remove `if (isMacOSX()) { System.setProperty apple.* }` block; keep `isMacOSX()` method (used elsewhere).
- [ ] **Step 2: Verify compile** `mvn compile`
- [ ] **Step 3: Commit** `git add src/main/java/com/colorpicker/Launcher.java && git commit -m "refactor: remove deprecated macOS system property setters"`

---

## Task 3: Upgrade pom.xml (Java 21, Latest Plugins, JaCoCo, AssertJ, headless)

**Files:**
- Modify: `pom.xml`

- [ ] **Step 1: Update properties** 17->21, junit 5.12.2, add assertj 3.27.0, jacoco 0.8.13, plugin versions 3.13.0/3.5.3/3.2.0/2.18.0
- [ ] **Step 2: Add AssertJ dependency** `assertj-core` test scope
- [ ] **Step 3: Replace build/plugins** compiler + surefire (with `<configuration><systemPropertyVariables><java.awt.headless>true</java.awt.headless></systemPropertyVariables></configuration>`) + exec + versions + jacoco (prepare-agent, report, check 0.60 LINE BUNDLE)
- [ ] **Step 4: Verify** `mvn clean verify` -> BUILD SUCCESS, `target/site/jacoco/index.html` exists
- [ ] **Step 5: Commit** `git add pom.xml && git commit -m "build: upgrade to Java 21, latest plugins, JaCoCo + AssertJ, surefire headless"`

---

## Task 4: Extract ColorModel

**Files:**
- Create: `src/main/java/com/colorpicker/ColorModel.java`
- Modify: `src/main/java/com/colorpicker/ColorPickerFrame.java`

- [ ] **Step 1: Create ColorModel.java** fields hue/sat/brightness 0.5f, getColor(), get/set hue/sat/brightness with clamp 0-1
- [ ] **Step 2: Refactor ColorPickerFrame** replace `hue,saturation,brightness` fields with `private final ColorModel colorModel = new ColorModel();` update updateColor, mouseWheelMoved, mouseClicked, addColorPanel to delegate; add @Serial to serialVersionUID; remove stale commented block in mouseWheelMoved
- [ ] **Step 3: Verify** `mvn compile && mvn test`
- [ ] **Step 4: Commit** `git add src/main/java/com/colorpicker/ColorModel.java src/main/java/com/colorpicker/ColorPickerFrame.java && git commit -m "refactor: extract ColorModel for testability, clean stale comments"`

---

## Task 5: Expand ColorUtilsTest

**Files:**
- Modify: `src/test/java/com/colorpicker/ColorUtilsTest.java`

- [ ] **Step 1: Replace with parameterized + AssertJ** WHITE/BLACK explicit tests + @ParameterizedTest @MethodSource colorProvider (7 cases: 10,20,30/0A141E etc) + edge case; use assertThat
- [ ] **Step 2: Run** `mvn test -Dtest=ColorUtilsTest` -> 9+ pass
- [ ] **Step 3: Commit** `git add src/test/java/com/colorpicker/ColorUtilsTest.java && git commit -m "test: expand ColorUtilsTest with parameterized tests + AssertJ"`

---

## Task 6: Create ColorModelTest

**Files:**
- Create: `src/test/java/com/colorpicker/ColorModelTest.java`

- [ ] **Step 1: Create test** 12 tests: default 0.5, getColor notNull, clampAbove/Below for hue/sat/brightness (6), acceptsValid (3), getColor changes with hue
- [ ] **Step 2: Run** `mvn test -Dtest=ColorModelTest`
- [ ] **Step 3: Commit** `git add src/test/java/com/colorpicker/ColorModelTest.java && git commit -m "test: add comprehensive ColorModel unit tests"`

---

## Task 7: Create ColorPickerFrameHeadlessTest

**Files:**
- Create: `src/test/java/com/colorpicker/ColorPickerFrameHeadlessTest.java`

- [ ] **Step 1: Create headless test** JUnit 5, assume headless, tests: frame canBeInstantiated, getTitle ColorPicker, hasStatusBar, **addColorPanel adds component** (call addColorPanel via reflection or verify getContentPane count increases after construction + direct add), verify colorModel integration. Runs under java.awt.headless=true from surefire config; no manual System.setProperty needed per-test but include @BeforeAll setProperty as defense.
- [ ] **Step 2: Run** `mvn test -Dtest=ColorPickerFrameHeadlessTest`
- [ ] **Step 3: Full verify** `mvn verify` coverage >=60%
- [ ] **Step 4: Commit** `git add src/test/java/com/colorpicker/ColorPickerFrameHeadlessTest.java && git commit -m "test: add headless Swing tests for ColorPickerFrame including addColorPanel"`

---

## Task 8: Enhance CI Workflow

**Files:**
- Modify: `.github/workflows/ci.yml`

- [ ] **Step 1: Replace ci.yml** JDK 21 temurin cache maven, `mvn clean verify`, upload jacoco artifact always()
- [ ] **Step 2: Commit** `git add .github/workflows/ci.yml && git commit -m "ci: upgrade to JDK 21, add JaCoCo gate and report upload"`

---

## Task 9: Create release-please Workflows (split) + Delete Old

**Files:**
- Create: `.github/workflows/release-please.yml`
- Create: `.github/workflows/publish-release.yml`
- Create: `.release-please-config.json`
- Delete: `.github/workflows/release.yml`

- [ ] **Step 1: Create .release-please-config.json** changelog-types, packages . release-type java
- [ ] **Step 2: Create release-please.yml** on push master, contents:write/pull-requests:write, single job googleapis/release-please-action@v4 with config-file
- [ ] **Step 3: Create publish-release.yml** on push master + `needs` via workflow_run or separate release_created check; use `on: release: types: [published]` or `workflow_run` filtering. Use softprops/action-gh-release@v2 to upload `target/color-picker-*.jar` after `mvn clean package -DskipTests`. Simplest: `on: push: tags: v*` or `on: release: types: [created]`. Use documented pattern: `on: release: types: [published]` + build.
- [ ] **Step 4: Delete old** `rm .github/workflows/release.yml`
- [ ] **Step 5: Commit** `git add .github/workflows/release-please.yml .github/workflows/publish-release.yml .release-please-config.json && git rm .github/workflows/release.yml && git commit -m "ci: add split release-please + publish workflows, remove manual release"`

---

## Task 10: Dependabot Config

**Files:**
- Create: `.github/dependabot.yml`

- [ ] **Step 1: Create** maven weekly + github-actions monthly
- [ ] **Step 2: Commit** `git add .github/dependabot.yml && git commit -m "ci: add dependabot for Maven + Actions"`

---

## Task 11: CHANGELOG.md

**Files:**
- Create: `CHANGELOG.md`

- [ ] **Step 1: Create skeleton** Keep a Changelog + SemVer, Unreleased: Added/Changed/Removed with JUnit 5.12.2, Java21, ColorModel, etc
- [ ] **Step 2: Commit** `git add CHANGELOG.md && git commit -m "docs: add CHANGELOG skeleton"`

---

## Task 12: Update README.md

**Files:**
- Modify: `README.md`

- [ ] **Step 1: Java 8->21 and add CI badge**
- [ ] **Step 2: Commit** `git add README.md && git commit -m "docs: update README for Java 21 and CI badge"`

---

## Task 13: Final Verification

- [ ] **Step 1: Run** `mvn clean verify` -> BUILD SUCCESS coverage >=60%
- [ ] **Step 2: Check git log** `git log --oneline -20`
- [ ] **Step 3: No extra push yet** (final review handles)

---

## Task 14: Workspace Cleanup (handled by SDD final review)

