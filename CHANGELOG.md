<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Flutter Json Format New Generation

## [4.0.0]

Breaking changes.

Generating [freezed](https://pub.dev/packages/freezed) models
with [json_serializable](https://pub.dev/packages/freezed#fromjsontojson) support 
from JSON examples.

Advantages: Easy to modify models according to changes on backend.
Disadvantages: Need to add a code generation to the project (see 'dart/pubspec.yaml' and [freezed](https://pub.dev/packages/freezed) docs)

**But a modern project using flutter/dart should have code generation.**

## [3.0.4]
- Deserialized model - result of Model.fromMap() -  can be null, especially for recursive cases.

## [3.0.4]
### Added
- Null safety variant of generated files

### Changed
- Refactored based on (https://github.com/JetBrains/intellij-platform-plugin-template)[IntelliJ Platform Plugin Template]
