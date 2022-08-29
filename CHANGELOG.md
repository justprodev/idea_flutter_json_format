# Change Log

## [4.0.1]

Breaking changes.

Generating [freezed](https://pub.dev/packages/freezed) models
with [json_serializable](https://pub.dev/packages/freezed#fromjsontojson) support 
from JSON examples.

Advantages: Easy to modify models according to changes on backend.
Disadvantages: Need to add a code generation to the project (see 'dart/pubspec.yaml' and [freezed](https://pub.dev/packages/freezed) docs)

**Enhancement**

- New UI
- Added ability to generate classes from the "New" menu
- Validating JSON
- Formatting JSON

## [3.1.3]
- Deserialized model - result of Model.fromMap() -  can be null, especially for recursive cases.

### Added
- Null safety variant of generated files

### Changed
- Refactored based on (https://github.com/JetBrains/intellij-platform-plugin-template)[IntelliJ Platform Plugin Template]
