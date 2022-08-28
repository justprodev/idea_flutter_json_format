import 'dart:convert';
import 'dart:io';

import 'package:test/test.dart';
import 'TestModel.dart';

void main() {
  group('json', () {
    test('serialize and deserialize', () async {
      // read json source
      final plainJson1 = await File('test2.json').readAsString();
      // create model from source
      final model1 = TestModel.fromMap(json.decode(plainJson1));

      // reconstruct json source from model file
      final plainJson2 = json.encode(model1.toJson());
      // create model again from reconstructed source
      final model2 = TestModel.fromMap(json.decode(plainJson2));

      // compare deserialized jsons from the models
      expect(true, model2.toJson().toString() == model1.toJson().toString());
    });
  });
}