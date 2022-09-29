[![Version](https://img.shields.io/jetbrains/plugin/v/16971-dartjsongenerator.svg)](https://plugins.jetbrains.com/plugin/16971-dartjsongenerator)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/16971-dartjsongenerator.svg)](https://plugins.jetbrains.com/plugin/16971-dartjsongenerator)

### Generating [freezed](https://pub.dev/packages/freezed) models with [json_serializable](https://pub.dev/packages/freezed#fromjsontojson) support from JSON examples.

Generating from the *New* menu:

![new](/screenshots/new.png)

Generating from the *Generate* menu:

![new](/screenshots/generate.png)

Generating dialog:

![new](/screenshots/dialog.png)

JSON:
```json
{
  "code": "2000",
  "data": {
    "test_field": "aaa",
    "facetofaceAddress": {
      "address": "",
      "receiver": "罗",
      "receiverCellphone": "1388345****",
      "sid": "001190e19e754001b53701d0aa81bfe0",
      "sortNumber": 1,
      "title": "女士",
      "type": "FACETOFACE",
      "userSid": "a19cf6e3586143d283dd4128c456bfaf"
    },
    "order": {
      "bidding": 2300,
      "brokerAvatar": "broker/getAvatar?key=avatar/b82378ccaccb403c9d8420274372c904",
      "brokerCellphone": "1347282****",
      "brokerDealNum": 15,
      "brokerName": "王**",
      "brokerSid": "b82378ccaccb403c9d8420274372c904",
      "brokerStars": 4.5,
      "code": "1512151307270113",
      "cover": "show/getPoster?key=52f30bbce4ef4122919cbc95c2f01c36/52f30bbce4ef4122919cbc95c2f01c36",
      "createdDatetime": "2015-12-15 13:07:28",
      "deliveryAddressSid": "c25c1954ca204dee8fc18f51bcc71a3e",
      "deliveryFee": 0,
      "evaluateStarts": 0,
      "isDelete": false,
      "isSequential": false,
      "orderStatus": "CLOSED",
      "orderStatusArray": [{
        "operateDatetime": "2015-12-15 13:07:28",
        "operateUserSid": "b82378ccaccb403c9d8420274372c904",
        "operateUsername": "王**",
        "orderSid": "21bc3cc65e9e47af952c1f4f1f0fd85a",
        "orderType": "1",
        "sid": "04940ed81540466ea4408f79989a5d54",
        "state": "TAKING"
      }],
      "orderStatusDesp": "",
      "orderType": "1",
      "payType": "",
      "postTicketSid": "65cc6d54300349e984134ecd0faf3ede",
      "receiveDatetime": "2015-12-15 13:07:28",
      "receiver": "张**",
      "receiverAddress": "控江路1209",
      "receiverCellphone": "1502379****",
      "receiverTitle": "先生",
      "remark": "",
      "requestDatetime": "2015-12-15 13:07:28",
      "showName": "Love Radio 品冠 现在你在哪里 巡回演唱会上海站",
      "showSchedule": "2016-01-09 19:30:00",
      "showScheduleSid": "eecfd0657fb445a7a36abedc9b621c89",
      "showSid": "52f30bbce4ef4122919cbc95c2f01c36",
      "sid": "21bc3cc65e9e47af952c1f4f1f0fd85a",
      "stateDesp": "已关闭",
      "ticketPrice": 88000,
      "ticketQuantity": 1,
      "ticketSid": "c4583aa8a79a478e8e5cd14691028430",
      "totalPrice": 2300,
      "tradeType": "EXPRESS",
      "userCellphone": "1502379****",
      "userLeaveMessage": "",
      "userSid": "a19cf6e3586143d283dd4128c456bfaf",
      "venueAddress": "上海市长宁区武夷路777号",
      "venueName": "上海国际体操中心"
    }
  },
  "testFromList": [
    {
      "testKey" : "testValue"
    }
  ],
  "extraData": {},
  "message": "",
  "success": true,
  "intList": [1, 2, 3],
  "boolList": [true, false, true, true],
  "doubleList": [0.0, 1.1, 2.2, 3.3],
  "emptyList": [],
  "nullList": [null],
  "nullObj": null
}
```
Dart (named **new_model.dart**):
```dart
import 'package:freezed_annotation/freezed_annotation.dart';

part 'new_model.freezed.dart';
part 'new_model.g.dart';

@freezed
class NewModel with _$NewModel {
  const factory NewModel({
    @JsonKey(name: 'code') String? code,
    @JsonKey(name: 'data') DataBean? data,
    @JsonKey(name: 'testFromList') List<TestFromListBean>? testFromList,
    @JsonKey(name: 'extraData') ExtraDataBean? extraData,
    @JsonKey(name: 'message') String? message,
    @JsonKey(name: 'success') bool? success,
    @JsonKey(name: 'intList') List<int>? intList,
    @JsonKey(name: 'boolList') List<bool>? boolList,
    @JsonKey(name: 'doubleList') List<double>? doubleList,
    @JsonKey(name: 'emptyList') List<dynamic>? emptyList,
    @JsonKey(name: 'nullList') List<dynamic>? nullList,
    @JsonKey(name: 'nullObj') dynamic? nullObj,
  }) = _NewModel;

  factory NewModel.fromJson(Map<String, Object?> json) => _$NewModelFromJson(json);
}

@freezed
class ExtraDataBean with _$ExtraDataBean {
  const factory ExtraDataBean() = _ExtraDataBean;

  factory ExtraDataBean.fromJson(Map<String, Object?> json) => _$ExtraDataBeanFromJson(json);
}

@freezed
class TestFromListBean with _$TestFromListBean {
  const factory TestFromListBean({
    @JsonKey(name: 'testKey') String? testKey,
  }) = _TestFromListBean;

  factory TestFromListBean.fromJson(Map<String, Object?> json) => _$TestFromListBeanFromJson(json);
}

@freezed
class DataBean with _$DataBean {
  const factory DataBean({
    @JsonKey(name: 'test_field') String? testField,
    @JsonKey(name: 'facetofaceAddress') FacetofaceAddressBean? facetofaceAddress,
    @JsonKey(name: 'order') OrderBean? order,
  }) = _DataBean;

  factory DataBean.fromJson(Map<String, Object?> json) => _$DataBeanFromJson(json);
}

@freezed
class OrderBean with _$OrderBean {
  const factory OrderBean({
    @JsonKey(name: 'bidding') int? bidding,
    @JsonKey(name: 'brokerAvatar') String? brokerAvatar,
    @JsonKey(name: 'brokerCellphone') String? brokerCellphone,
    @JsonKey(name: 'brokerDealNum') int? brokerDealNum,
    @JsonKey(name: 'brokerName') String? brokerName,
    @JsonKey(name: 'brokerSid') String? brokerSid,
    @JsonKey(name: 'brokerStars') double? brokerStars,
    @JsonKey(name: 'code') String? code,
    @JsonKey(name: 'cover') String? cover,
    @JsonKey(name: 'createdDatetime') String? createdDatetime,
    @JsonKey(name: 'deliveryAddressSid') String? deliveryAddressSid,
    @JsonKey(name: 'deliveryFee') int? deliveryFee,
    @JsonKey(name: 'evaluateStarts') int? evaluateStarts,
    @JsonKey(name: 'isDelete') bool? isDelete,
    @JsonKey(name: 'isSequential') bool? isSequential,
    @JsonKey(name: 'orderStatus') String? orderStatus,
    @JsonKey(name: 'orderStatusArray') List<OrderStatusArrayBean>? orderStatusArray,
    @JsonKey(name: 'orderStatusDesp') String? orderStatusDesp,
    @JsonKey(name: 'orderType') String? orderType,
    @JsonKey(name: 'payType') String? payType,
    @JsonKey(name: 'postTicketSid') String? postTicketSid,
    @JsonKey(name: 'receiveDatetime') String? receiveDatetime,
    @JsonKey(name: 'receiver') String? receiver,
    @JsonKey(name: 'receiverAddress') String? receiverAddress,
    @JsonKey(name: 'receiverCellphone') String? receiverCellphone,
    @JsonKey(name: 'receiverTitle') String? receiverTitle,
    @JsonKey(name: 'remark') String? remark,
    @JsonKey(name: 'requestDatetime') String? requestDatetime,
    @JsonKey(name: 'showName') String? showName,
    @JsonKey(name: 'showSchedule') String? showSchedule,
    @JsonKey(name: 'showScheduleSid') String? showScheduleSid,
    @JsonKey(name: 'showSid') String? showSid,
    @JsonKey(name: 'sid') String? sid,
    @JsonKey(name: 'stateDesp') String? stateDesp,
    @JsonKey(name: 'ticketPrice') int? ticketPrice,
    @JsonKey(name: 'ticketQuantity') int? ticketQuantity,
    @JsonKey(name: 'ticketSid') String? ticketSid,
    @JsonKey(name: 'totalPrice') int? totalPrice,
    @JsonKey(name: 'tradeType') String? tradeType,
    @JsonKey(name: 'userCellphone') String? userCellphone,
    @JsonKey(name: 'userLeaveMessage') String? userLeaveMessage,
    @JsonKey(name: 'userSid') String? userSid,
    @JsonKey(name: 'venueAddress') String? venueAddress,
    @JsonKey(name: 'venueName') String? venueName,
  }) = _OrderBean;

  factory OrderBean.fromJson(Map<String, Object?> json) => _$OrderBeanFromJson(json);
}

@freezed
class OrderStatusArrayBean with _$OrderStatusArrayBean {
  const factory OrderStatusArrayBean({
    @JsonKey(name: 'operateDatetime') String? operateDatetime,
    @JsonKey(name: 'operateUserSid') String? operateUserSid,
    @JsonKey(name: 'operateUsername') String? operateUsername,
    @JsonKey(name: 'orderSid') String? orderSid,
    @JsonKey(name: 'orderType') String? orderType,
    @JsonKey(name: 'sid') String? sid,
    @JsonKey(name: 'state') String? state,
  }) = _OrderStatusArrayBean;

  factory OrderStatusArrayBean.fromJson(Map<String, Object?> json) => _$OrderStatusArrayBeanFromJson(json);
}

@freezed
class FacetofaceAddressBean with _$FacetofaceAddressBean {
  const factory FacetofaceAddressBean({
    @JsonKey(name: 'address') String? address,
    @JsonKey(name: 'receiver') String? receiver,
    @JsonKey(name: 'receiverCellphone') String? receiverCellphone,
    @JsonKey(name: 'sid') String? sid,
    @JsonKey(name: 'sortNumber') int? sortNumber,
    @JsonKey(name: 'title') String? title,
    @JsonKey(name: 'type') String? type,
    @JsonKey(name: 'userSid') String? userSid,
  }) = _FacetofaceAddressBean;

  factory FacetofaceAddressBean.fromJson(Map<String, Object?> json) => _$FacetofaceAddressBeanFromJson(json);
}
```
