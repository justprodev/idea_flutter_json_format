import 'dart:convert';
import 'dart:io';

import 'package:test/test.dart';
import 'new_model.dart';

void main() {
  group('json', () {
    test('compare models', () async {
      final plainJson1 = await File('../src/test/resources/test.json').readAsString();

      final newModel = NewModel.fromJson(json.decode(plainJson1));

      // compare serialized model from json VS NewModel
      expect(true, json.encode(json.decode(plainJson1)) == json.encode(newModel.toJson()));
    });
  });
}