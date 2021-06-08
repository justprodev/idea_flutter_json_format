import 'dart:convert';
import 'dart:io';

import 'package:test/test.dart';
import 'TestModel.dart';

void main() {
  group('json', () {
    test('serialize and deserialize', () async {
      var json = await File('test2.json').readAsString();
      var map = JsonDecoder().convert(json);

      print(map);

      var map2 = TestModel.fromMap(map).toJson();

      print(map2);

      expect(true, map.toString() == map2.toString());
    });
  });
}