# artificial-intelligence-projects

This repository collects recovered, publish-eligible project material associated with 421. The contents are preserved as separate subprojects rather than presented as one integrated application.

The repository name is historical. The recovered files include an Android application, Prolog source files, and Python submissions; the available inventory alone does not establish that every project implements an artificial-intelligence method.

## Repository layout

| Path | Material | Notes |
| --- | --- | --- |
| [`projects/258540599-1/`](projects/258540599-1/) | Android/Gradle project | Includes an Android application under `cmsc436groupproject-main`. |
| [`projects/282317398/`](projects/282317398/) | Prolog source | Contains `part_1.pl` and `part_2.pl`. |
| [`projects/project-7/`](projects/project-7/) | Python source | Contains four recovered submission files. |

Each recovered directory that includes `metadata.yml` retains it as provenance material. Metadata content has not been reproduced here because it was not supplied in the recovered file inventory.

## Projects

### Android running-data application

**Location:** [`projects/258540599-1/cmsc436groupproject-main/`](projects/258540599-1/cmsc436groupproject-main/)

This is a Gradle-based Android project written primarily in Kotlin, with a Java `LocationService`. Its source and resource names indicate screens or components for running activity, local run data, groups, settings, and leaderboard entries. The project includes classes named `RunFragment`, `LocalRunFragment`, `GroupFragment`, `SettingsFragment`, `LeaderboardManager`, `DistanceUtils`, `Accelerometer`, `LocationService`, and timing utilities.

The application is preserved as a recovered group-project codebase. Individual authorship and contribution boundaries are not established by the provided file list.

#### Setup

1. Install an Android SDK and a compatible JDK/Gradle environment.
2. Copy `local.properties.example` to `local.properties` and set the local Android SDK path as required by the Android build tools.
3. Review [`app/google-services.json.example`](projects/258540599-1/cmsc436groupproject-main/app/google-services.json.example). If the configured Google Services/Firebase build integration requires a real configuration file, obtain one for a project you control and save it as `app/google-services.json`.
4. From the Android project directory, use the included Gradle wrapper:

```sh
./gradlew build
```

On Windows:

```bat
gradlew.bat build
```

#### Private-data and service requirements

No real `google-services.json` is included; only an example file is present. A Google Services/Firebase configuration may be required for a complete Android build or for features that depend on configured backend services. Such configuration is project-specific and should not be committed.

The presence of `LocationService` and location-related application code indicates that runtime location permission and device location services may be relevant. Actual permission flow, data collection behavior, backend synchronization, and retention behavior must be reviewed in the source before deployment.

#### Validation status

The repository contains Android test source paths with `ExampleUnitTest.kt` and `ExampleInstrumentedTest.kt`. Their presence does not establish that tests are substantive, configured successfully, or have been run. No build, lint, unit-test, instrumentation-test, or device-validation result was supplied.

#### Limitations

- The recovered package name remains `com.example.testlayout`.
- Backend configuration and credentials are intentionally absent.
- No emulator, device, or supported Android SDK version has been verified from the supplied inventory.
- Resource and class names suggest intended features but do not demonstrate complete or working behavior.

### Prolog source

**Location:** [`projects/282317398/`](projects/282317398/)

This subproject contains two Prolog files:

- [`part_1.pl`](projects/282317398/part_1.pl)
- [`part_2.pl`](projects/282317398/part_2.pl)

The files are retained as separate recovered source components. The available file list does not identify the predicates, expected queries, runtime version, input facts, or whether the second file depends on the first.

#### Setup

Use a Prolog implementation compatible with the source syntax. Load and inspect each file before selecting queries or combining the files:

```sh
cd projects/282317398
# Start the locally installed Prolog runtime, then consult part_1.pl or part_2.pl.
```

A specific implementation and version are not claimed because neither is identified by the supplied inventory.

#### Private-data requirements

No private dataset, credentials file, or external configuration file is visible in the supplied file list. Any facts, inputs, or dependencies embedded in the source must be reviewed directly.

#### Validation status

No test files, expected-output fixtures, runtime logs, or successful execution results were supplied. The files have not been represented as executed or validated.

#### Limitations

- The project purpose cannot be stated reliably without inspecting the source contents.
- No documented entry predicate or example query is available from the file list.
- Compatibility with a particular Prolog implementation is unresolved.

### Python submission collection

**Location:** [`projects/project-7/`](projects/project-7/)

This directory contains four Python files preserved under their recovered names:

- [`submission.py`](projects/project-7/submission.py)
- [`submission2.py`](projects/project-7/submission2.py)
- [`Submission3.py`](projects/project-7/Submission3.py)
- [`Submission4.py`](projects/project-7/Submission4.py)

The naming suggests multiple submission versions or components, but the file inventory does not establish their relationship, execution order, command-line interface, dependencies, or problem domain.

#### Setup

Use a Python interpreter compatible with the source. Before running a file, inspect imports, module-level execution, command-line argument handling, and file-path expectations.

Basic syntax compilation can be attempted without executing project logic:

```sh
cd projects/project-7
python -m py_compile submission.py submission2.py Submission3.py Submission4.py
```

The required Python version and third-party packages are not identified by the available inventory.

#### Private-data requirements

No separate dataset, credential file, or configuration file is listed. This does not prove that the programs do not expect private inputs, hard-coded paths, or externally supplied data. Review the source before publication or execution.

#### Validation status

No test suite, dependency manifest, sample input, expected output, or execution result was supplied. Syntax compilation is a recommended check, not a reported result.

#### Limitations

- The filenames do not provide a reliable project description.
- There is no documented Python version or dependency list.
- Relationships among the four files are unresolved.

## Validation

No validation command in this README has been reported as run. Commands are listed as reproducible starting points and may require local tools, configuration, dependencies, or source review.

## Provenance and publication notes

- The repository was assembled from recovered files identified as publish-eligible from 421.
- Original recovered directory identifiers, including `258540599-1` and `282317398`, are retained to preserve source organization.
- The Android project includes group-project naming in its recovered directory. The available inventory does not establish contribution ownership or permission beyond the stated publish-eligible recovery status.
- Assignment instructions, grading materials, hidden tests, private configuration, and secrets are not included in this index.
- Before further redistribution, review source headers, metadata, dependency licenses, service configuration, and collaboration/attribution obligations.
