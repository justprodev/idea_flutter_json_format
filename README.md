Forked from original plugin "flutter_json_format" added nullsafety variant of a generated files.

Original README below:

### Update
#### ver 2.1

generate 『toJson』 function to support converting object to map

```json
{
  "name": "zll",
  "age": 29,
  "star": 4.5,
  "married": true
}
```

```dart
class Test {
  String name;
  int age;
  double star;
  bool married;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.name = map['name'];
    testBean.age = map['age'];
    testBean.star = map['star'];
    testBean.married = map['married'];
    return testBean;
  }

  Map toJson() => {
    "name": name,
    "age": age,
    "star": star,
    "married": married,
  };
}
```

```dart
import 'dart:convert'

var json = JsonEncoder().convert(obj);
```

### Install
#### 1. Download jar
https://plugins.jetbrains.com/plugin/11551-dart-json-format

#### 2. Search in IDE

Plugins -> Browse repositories -> input "dart_json_format"

### Generate

![gif](example_image/jsonformat.gif)

### Examples

#### 1. Simple data

```json
{
  "name": "zll",
  "age": 29,
  "star": 4.5,
  "married": true
}
```

```dart
class Test {
  String name;
  int age;
  double star;
  bool married;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.name = map['name'];
    testBean.age = map['age'];
    testBean.star = map['star'];
    testBean.married = map['married'];
    return testBean;
  }
}
```

#### 2. Ojbect

```json
{
  "programmer": {
    "name": "zll",
    "age": 29,
    "star": 4.5,
    "married": true
  }
}
```

```dart
class Test {
  ProgrammerBean programmer;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.programmer = ProgrammerBean.fromMap(map['programmer']);
    return testBean;
  }
}

class ProgrammerBean {
  String name;
  int age;
  double star;
  bool married;

  static ProgrammerBean fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    ProgrammerBean programmerBean = ProgrammerBean();
    programmerBean.name = map['name'];
    programmerBean.age = map['age'];
    programmerBean.star = map['star'];
    programmerBean.married = map['married'];
    return programmerBean;
  }
}
```

#### 3. Array

```json
{
  "names": ["zll", "kfc"],
  "ages": [29, 25],
  "stars": [4.5, 4.4],
  "marrieds": [true, false]
}
```

```dart
class Test {
  List<String> names;
  List<int> ages;
  List<double> stars;
  List<bool> marrieds;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.names = []..addAll(
      (map['names'] as List ?? []).map((o) => o.toString())
    );
    testBean.ages = []..addAll(
      (map['ages'] as List ?? []).map((o) => int.tryParse(o.toString()))
    );
    testBean.stars = []..addAll(
      (map['stars'] as List ?? []).map((o) => double.tryParse(o.toString()))
    );
    testBean.marrieds = []..addAll(
      (map['marrieds'] as List ?? []).map((o) => o.toString() == 'true')
    );
    return testBean;
  }
}
```

#### 4. Array of Object
```json
{
  "programmers": [
    {
      "name": "zll",
      "age": 29,
      "star": 4.5,
      "married": true
    },{
      "name": "kfc",
      "age": 25,
      "star": 4.1,
      "married": false
    }
  ]
}
```

```dart
class Test {
  List<ProgrammersBean> programmers;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.programmers = []..addAll(
      (map['programmers'] as List ?? []).map((o) => ProgrammersBean.fromMap(o))
    );
    return testBean;
  }
}

class ProgrammersBean {
  String name;
  int age;
  double star;
  bool married;

  static ProgrammersBean fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    ProgrammersBean programmersBean = ProgrammersBean();
    programmersBean.name = map['name'];
    programmersBean.age = map['age'];
    programmersBean.star = map['star'];
    programmersBean.married = map['married'];
    return programmersBean;
  }
}
```

#### 5. Nested Array
```json
{
  "something": [[[[[1]]]]]
}
```

```dart
class Test {
  List<List<List<List<List<int>>>>> something;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.something = []..addAll(
      (map['something'] as List ?? []).map((o) => []..addAll((o as List ?? []).map((oo) => []..addAll((oo as List ?? []).map((ooo) => []..addAll((ooo as List ?? []).map((oooo) => []..addAll((oooo as List ?? []).map((ooooo) => int.tryParse(ooooo.toString()))))))))))
    );
    return testBean;
  }
}
```

#### 5. Nested Array of Object
```json
{
  "something": [[[[[{
    "name": "zll",
    "age": 29,
    "star": 4.5,
    "married": true
  }]]]]]
}
```

```dart
class Test {
  List<List<List<List<List<SomethingBean>>>>> something;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.something = []..addAll(
      (map['something'] as List ?? []).map((o) => []..addAll((o as List ?? []).map((oo) => []..addAll((oo as List ?? []).map((ooo) => []..addAll((ooo as List ?? []).map((oooo) => []..addAll((oooo as List ?? []).map((ooooo) => SomethingBean.fromMap(ooooo))))))))))
    );
    return testBean;
  }
}

class SomethingBean {
  String name;
  int age;
  double star;
  bool married;

  static SomethingBean fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    SomethingBean somethingBean = SomethingBean();
    somethingBean.name = map['name'];
    somethingBean.age = map['age'];
    somethingBean.star = map['star'];
    somethingBean.married = map['married'];
    return somethingBean;
  }
}
```

#### 6. Empty or Null
```json
{
  "obj": null,
  "emptyList": [],
  "nullList": [null]
}
```

```dart
class Test {
  dynamic obj;
  List<dynamic> emptyList;
  List<dynamic> nullList;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.obj = map['obj'];
    testBean.emptyList = map['emptyList'];
    testBean.nullList = map['nullList'];
    return testBean;
  }
}
```

#### 7. Root Array
```json
[
  {
    "name": "zll",
    "age": 29,
    "star": 4.5,
    "married": true
  },
  {
	"name": "kfc",
	"age": 25,
	"star": 4.1,
	"married": false
  }
]
```

##### Take only array[0] to use

```dart
class Test {
  String name;
  int age;
  double star;
  bool married;

  static Test fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    Test testBean = Test();
    testBean.name = map['name'];
    testBean.age = map['age'];
    testBean.star = map['star'];
    testBean.married = map['married'];
    return testBean;
  }
}
```

