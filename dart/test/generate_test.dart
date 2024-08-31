import 'dart:convert';
import 'dart:io';

import 'package:flutter_format_ng_test/model/default_model.dart';
import 'package:flutter_format_ng_test/model/standard_model.dart';
import 'package:test/test.dart';

void main() {
  group('json', () {
    test('standard', () async {
      final json = jsonDecode(await File('../src/test/resources/test.json').readAsString());
      final model = StandardModel.fromJson(json);
      compare(json, model.toJson);
    });

    test('default', () async {
      final json = jsonDecode(await File('../src/test/resources/test_default.json').readAsString());
      final model = DefaultModel.fromJson(json);
      compare(json, model.toJson);
      const defaultModel = DefaultModel();
      defaultModel.stringV == '';
      defaultModel.listV == [];
      defaultModel.boolV == false;
      defaultModel.intV == 0;
      defaultModel.doubleV == 0.0;
    });
  });
}

void compare(dynamic json, Map<String, dynamic> Function() model) {
  // compare serialized model from json VS Model
  expect(
    true,
    jsonEncode(json) == jsonEncode(model()),
    reason: 'serialized model from json != model',
  );
}
