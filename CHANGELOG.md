# Change Log

## [4.0.5]

**BugFix**

- fixed old bug with writing file which become critical in 2023.3

## [4.0.4]

**BugFix**

- fixed a parts section when generating code from "Generate" dialog

## [4.0.3]

**BugFix**

- removed auto-format when copy&paste (now is buggy, will be implemented later); show dialog relative to ide window

## [4.0.1]

**Enhancement**

- Generating [freezed](https://pub.dev/packages/freezed) models
  with [json_serializable](https://pub.dev/packages/freezed#fromjsontojson) (breaking change)
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
