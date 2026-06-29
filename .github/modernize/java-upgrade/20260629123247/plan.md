# Upgrade Plan: Community Forum Backend (20260629123247)

- **Generated**: 2026-06-29 12:32:47
- **HEAD Branch**: N/A (version control not available)
- **HEAD Commit ID**: N/A (version control not available)

> Note: This project is not a git repository. Changes will be made to the working directory but not tracked via version control.

## Available Tools

**JDKs**
- Java 23: C:\Program Files\Java\jdk-23\bin (available, but not LTS)
- Java 24: C:\Program Files\Java\jdk-24\bin (available, but not LTS)
- Java 26: C:\Program Files\Java\jdk-26\bin (available, but not LTS)
- Java 26.0.1: C:\Users\jaypr\.jdks\openjdk-26.0.1\bin (available, but not LTS)
- JDK 21 (LTS): **<TO_BE_INSTALLED>** (required by step 3)

**Build Tools**
- Maven Wrapper: 3.9.16 (compatible with Java 21, no upgrade needed)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

- Ensure Spring Boot 3.3.1 compatibility is maintained (already compatible with Java 21)
- Verify MongoDB driver compatibility with Java 21
- No breaking changes expected from Java 17 → Java 21

## Options

- Working branch: appmod/java-upgrade-20260629123247
- Run tests before and after the upgrade: true

## Upgrade Goals

- **Target Java Version**: 21 (LTS)

## Technology Stack

| Technology/Dependency | Current | Min Compatible | Why Incompatible |
| -------------------- | ------- | -------------- | --------------- |
| Java | 17 | 21 | User requested upgrade to latest LTS |
| Spring Boot | 3.3.1 | 3.3.1 | Already compatible with Java 21; no upgrade needed |
| Maven Wrapper | 3.9.16 | 3.9.16 | Fully compatible with Java 21; no upgrade needed |
| Lombok | 1.18.30 (managed by SB 3.3.1) | 1.18.30 | Compatible with Java 21 |
| Spring Data MongoDB | (managed by SB 3.3.1) | (managed) | Compatible with Java 21 |
| MongoDB Java Driver | (managed by SB 3.3.1) | (managed) | Compatible with Java 21 |

## Derived Upgrades

- **None**: Spring Boot 3.3.1 is modern and compatible with Java 21. No framework, dependency, or configuration changes are required beyond updating the Java version property.

## Impact Analysis

### Subsection: Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
| ---- | ---------- | ------- | ------ | ------ | ------ |
| pom.xml | java.version property | 17 | upgrade | 21 | User requested - upgrade to latest LTS |

### Subsection: Source Code Changes

| File | Location | Current | Required Change | Reason |
| ---- | -------- | ------- | --------------- | ------ |
| (none) | (none) | (none) | (none) | Spring Boot 3.3.1 introduces no breaking changes when compiling with Java 21. No source code modifications required. |

### Subsection: Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
| ---- | --------------- | ------- | --------------- | ------ |
| (none) | (none) | (none) | (none) | No configuration changes required. Spring Boot 3.3.1 works with Java 21 out of the box. |

### Subsection: CI/CD Changes

| File | Location | Current | Required Change |
| ---- | -------- | ------- | --------------- |
| (none) | (none) | (none) | No CI/CD files detected in the repository (no Dockerfile, azure-pipelines.yml, etc.). |

### Subsection: Risks & Warnings

- **None**: Java 17 → Java 21 is a backward-compatible upgrade within the LTS line. Spring Boot 3.3.1 has been tested and verified to work with Java 21. No API removals, namespace migrations, or reflection-based code issues are anticipated.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Install JDK 21 (LTS) to enable compilation and testing with the target Java version
  - **Changes to Make**: Install JDK 21 using appmod-install-jdk
  - **Verification**: Command: `#appmod-list-jdks --version 21`
  - **Expected Result**: JDK 21 available and listed

- Step 2: Setup Baseline
  - **Rationale**: Record current test status with Java 17 as baseline for acceptance criteria
  - **Changes to Make**: Compile and run tests using current JDK 17
  - **Verification**: Command: `mvnw clean compile test-compile && mvnw clean test`
  - **Expected Result**: All tests pass (or record pass rate if any fail), baseline established

- Step 3: Upgrade Java Version to 21
  - **Rationale**: Update the java.version property in pom.xml to target Java 21
  - **Changes to Make**: pom.xml: change `<java.version>17</java.version>` to `<java.version>21</java.version>`
  - **Verification**: Command: `mvnw clean test-compile -q`
  - **Expected Result**: Compilation succeeds with Java 21

- Step 4: Final Validation
  - **Rationale**: Verify all goals met, resolve any test failures, confirm 100% test pass rate
  - **Changes to Make**: Fix any test failures discovered during compilation/test phase
  - **Verification**: Command: `mvnw clean test -q`
  - **Expected Result**: All tests pass with Java 21 (100% pass rate or equal to baseline)

---

**Next Steps**: Review this plan and confirm to proceed with the upgrade.
