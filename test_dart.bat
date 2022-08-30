cd dart || exit 1
dart pub get
dart run build_runner build --delete-conflicting-outputs
dart lib/main.dart
