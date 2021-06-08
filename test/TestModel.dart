/// code : "2000"
/// data : {"objectArray":[{"a":1,"b":2},{"a":3,"b":4},{"a":5,"b":6}],"expressAddress":{"address":"???1209","createdDatetime":"2015-12-09 19:36:38","receiver":"?**","receiverCellphone":"1502379****","sid":"c25c1954ca204dee8fc18f51bcc71a3e","sortNumber":1,"title":"??","type":"EXPRESS","userSid":"a19cf6e3586143d283dd4128c456bfaf"},"facetofaceAddress":{"address":"","receiver":"?","receiverCellphone":"1388345****","sid":"001190e19e754001b53701d0aa81bfe0","sortNumber":1,"title":"??","type":"FACETOFACE","userSid":"a19cf6e3586143d283dd4128c456bfaf"},"order":{"bidding":2300,"brokerAvatar":"broker/getAvatar?key=avatar/b82378ccaccb403c9d8420274372c904","brokerCellphone":"1347282****","brokerDealNum":15,"brokerName":"?**","brokerSid":"b82378ccaccb403c9d8420274372c904","brokerStars":4.5,"code":"1512151307270113","cover":"show/getPoster?key=52f30bbce4ef4122919cbc95c2f01c36/52f30bbce4ef4122919cbc95c2f01c36","createdDatetime":"2015-12-15 13:07:28","deliveryAddressSid":"c25c1954ca204dee8fc18f51bcc71a3e","deliveryFee":0,"evaluateStarts":0,"isDelete":false,"isSequential":false,"orderStatus":"CLOSED","orderStatusArray":[{"operateDatetime":"2015-12-15 13:07:28","operateUserSid":"b82378ccaccb403c9d8420274372c904","operateUsername":"?**","orderSid":"21bc3cc65e9e47af952c1f4f1f0fd85a","orderType":"1","sid":"04940ed81540466ea4408f79989a5d54","state":"TAKING"}],"orderStatusDesp":"","orderType":"1","payType":"","postTicketSid":"65cc6d54300349e984134ecd0faf3ede","receiveDatetime":"2015-12-15 13:07:28","receiver":"?**","receiverAddress":"???1209","receiverCellphone":"1502379****","receiverTitle":"??","remark":"","requestDatetime":"2015-12-15 13:07:28","showName":"Love Radio ?? ?????? ????????","showSchedule":"2016-01-09 19:30:00","showScheduleSid":"eecfd0657fb445a7a36abedc9b621c89","showSid":"52f30bbce4ef4122919cbc95c2f01c36","sid":"21bc3cc65e9e47af952c1f4f1f0fd85a","stateDesp":"???","ticketPrice":88000,"ticketQuantity":1,"ticketSid":"c4583aa8a79a478e8e5cd14691028430","totalPrice":2300,"tradeType":"EXPRESS","userCellphone":"1502379****","userLeaveMessage":"","userSid":"a19cf6e3586143d283dd4128c456bfaf","venueAddress":"?????????777?","venueName":"????????"}}
/// testFromList : [{"testKey":"testValue"}]
/// extraData : {}
/// message : ""
/// success : true
/// intList : [1,2,3]
/// boolList : [true,false,true,true]
/// doubleList : [0.0,1.1,2.2,3.3]
/// emptyList : []
/// nullList : [null]
/// nullObj : null

class TestModel {
  final String? code;
  final DataBean? data;
  final List<TestFromListBean>? testFromList;
  final ExtraDataBean? extraData;
  final String? message;
  final bool? success;
  final List<int>? intList;
  final List<bool>? boolList;
  final List<double>? doubleList;
  final List<dynamic>? emptyList;
  final List<dynamic>? nullList;
  final dynamic? nullObj;

  TestModel(this.code, this.data, this.testFromList, this.extraData, this.message, this.success, this.intList, this.boolList, this.doubleList, this.emptyList, this.nullList, this.nullObj);

  static TestModel fromMap(Map<String, dynamic> map) {
    TestModel testModelBean = TestModel(
     map['code'],
     map['data']!=null ? DataBean.fromMap(map['data']) : null,
     map['testFromList']!=null ? ([]..addAll(
       (map['testFromList'] as List).map((o) => TestFromListBean.fromMap(o))
     )) : null,
     map['extraData']!=null ? ExtraDataBean.fromMap(map['extraData']) : null,
     map['message'],
     map['success'],
     map['intList']!=null ? ([]..addAll(
       (map['intList'] as List).map((o) => int.tryParse(o.toString()) ?? 0)
     )) : null,
     map['boolList']!=null ? ([]..addAll(
       (map['boolList'] as List).map((o) => o.toString() == 'true')
     )) : null,
     map['doubleList']!=null ? ([]..addAll(
       (map['doubleList'] as List).map((o) => double.tryParse(o.toString()) ?? 0.0)
     )) : null,
     map['emptyList'],
     map['nullList'],
     map['nullObj'],
    );
    return testModelBean;
  }

  Map toJson() => {
    "code": code,
    "data": data?.toJson(),
    "testFromList": testFromList?.map((o)=>o.toJson()).toList(growable: false),
    "extraData": extraData?.toJson(),
    "message": message,
    "success": success,
    "intList": intList,
    "boolList": boolList,
    "doubleList": doubleList,
    "emptyList": emptyList,
    "nullList": nullList?.map((o) {try{ return o.toJson(); }catch(e){ return o; }}).toList(growable: false),
    "nullObj": nullObj?.toJson(),
  }..removeWhere((k,v)=>v==null);
}


class ExtraDataBean {

  ExtraDataBean();

  static ExtraDataBean fromMap(Map<String, dynamic> map) {
    ExtraDataBean extraDataBean = ExtraDataBean(
    );
    return extraDataBean;
  }

  Map toJson() => {
  }..removeWhere((k,v)=>v==null);
}

/// testKey : "testValue"

class TestFromListBean {
  final String? testKey;

  TestFromListBean(this.testKey);

  static TestFromListBean fromMap(Map<String, dynamic> map) {
    TestFromListBean testFromListBean = TestFromListBean(
     map['testKey'],
    );
    return testFromListBean;
  }

  Map toJson() => {
    "testKey": testKey,
  }..removeWhere((k,v)=>v==null);
}

/// objectArray : [{"a":1,"b":2},{"a":3,"b":4},{"a":5,"b":6}]
/// expressAddress : {"address":"???1209","createdDatetime":"2015-12-09 19:36:38","receiver":"?**","receiverCellphone":"1502379****","sid":"c25c1954ca204dee8fc18f51bcc71a3e","sortNumber":1,"title":"??","type":"EXPRESS","userSid":"a19cf6e3586143d283dd4128c456bfaf"}
/// facetofaceAddress : {"address":"","receiver":"?","receiverCellphone":"1388345****","sid":"001190e19e754001b53701d0aa81bfe0","sortNumber":1,"title":"??","type":"FACETOFACE","userSid":"a19cf6e3586143d283dd4128c456bfaf"}
/// order : {"bidding":2300,"brokerAvatar":"broker/getAvatar?key=avatar/b82378ccaccb403c9d8420274372c904","brokerCellphone":"1347282****","brokerDealNum":15,"brokerName":"?**","brokerSid":"b82378ccaccb403c9d8420274372c904","brokerStars":4.5,"code":"1512151307270113","cover":"show/getPoster?key=52f30bbce4ef4122919cbc95c2f01c36/52f30bbce4ef4122919cbc95c2f01c36","createdDatetime":"2015-12-15 13:07:28","deliveryAddressSid":"c25c1954ca204dee8fc18f51bcc71a3e","deliveryFee":0,"evaluateStarts":0,"isDelete":false,"isSequential":false,"orderStatus":"CLOSED","orderStatusArray":[{"operateDatetime":"2015-12-15 13:07:28","operateUserSid":"b82378ccaccb403c9d8420274372c904","operateUsername":"?**","orderSid":"21bc3cc65e9e47af952c1f4f1f0fd85a","orderType":"1","sid":"04940ed81540466ea4408f79989a5d54","state":"TAKING"}],"orderStatusDesp":"","orderType":"1","payType":"","postTicketSid":"65cc6d54300349e984134ecd0faf3ede","receiveDatetime":"2015-12-15 13:07:28","receiver":"?**","receiverAddress":"???1209","receiverCellphone":"1502379****","receiverTitle":"??","remark":"","requestDatetime":"2015-12-15 13:07:28","showName":"Love Radio ?? ?????? ????????","showSchedule":"2016-01-09 19:30:00","showScheduleSid":"eecfd0657fb445a7a36abedc9b621c89","showSid":"52f30bbce4ef4122919cbc95c2f01c36","sid":"21bc3cc65e9e47af952c1f4f1f0fd85a","stateDesp":"???","ticketPrice":88000,"ticketQuantity":1,"ticketSid":"c4583aa8a79a478e8e5cd14691028430","totalPrice":2300,"tradeType":"EXPRESS","userCellphone":"1502379****","userLeaveMessage":"","userSid":"a19cf6e3586143d283dd4128c456bfaf","venueAddress":"?????????777?","venueName":"????????"}

class DataBean {
  final List<ObjectArrayBean>? objectArray;
  final ExpressAddressBean? expressAddress;
  final FacetofaceAddressBean? facetofaceAddress;
  final OrderBean? order;

  DataBean(this.objectArray, this.expressAddress, this.facetofaceAddress, this.order);

  static DataBean fromMap(Map<String, dynamic> map) {
    DataBean dataBean = DataBean(
     map['objectArray']!=null ? ([]..addAll(
       (map['objectArray'] as List).map((o) => ObjectArrayBean.fromMap(o))
     )) : null,
     map['expressAddress']!=null ? ExpressAddressBean.fromMap(map['expressAddress']) : null,
     map['facetofaceAddress']!=null ? FacetofaceAddressBean.fromMap(map['facetofaceAddress']) : null,
     map['order']!=null ? OrderBean.fromMap(map['order']) : null,
    );
    return dataBean;
  }

  Map toJson() => {
    "objectArray": objectArray?.map((o)=>o.toJson()).toList(growable: false),
    "expressAddress": expressAddress?.toJson(),
    "facetofaceAddress": facetofaceAddress?.toJson(),
    "order": order?.toJson(),
  }..removeWhere((k,v)=>v==null);
}

/// bidding : 2300
/// brokerAvatar : "broker/getAvatar?key=avatar/b82378ccaccb403c9d8420274372c904"
/// brokerCellphone : "1347282****"
/// brokerDealNum : 15
/// brokerName : "?**"
/// brokerSid : "b82378ccaccb403c9d8420274372c904"
/// brokerStars : 4.5
/// code : "1512151307270113"
/// cover : "show/getPoster?key=52f30bbce4ef4122919cbc95c2f01c36/52f30bbce4ef4122919cbc95c2f01c36"
/// createdDatetime : "2015-12-15 13:07:28"
/// deliveryAddressSid : "c25c1954ca204dee8fc18f51bcc71a3e"
/// deliveryFee : 0
/// evaluateStarts : 0
/// isDelete : false
/// isSequential : false
/// orderStatus : "CLOSED"
/// orderStatusArray : [{"operateDatetime":"2015-12-15 13:07:28","operateUserSid":"b82378ccaccb403c9d8420274372c904","operateUsername":"?**","orderSid":"21bc3cc65e9e47af952c1f4f1f0fd85a","orderType":"1","sid":"04940ed81540466ea4408f79989a5d54","state":"TAKING"}]
/// orderStatusDesp : ""
/// orderType : "1"
/// payType : ""
/// postTicketSid : "65cc6d54300349e984134ecd0faf3ede"
/// receiveDatetime : "2015-12-15 13:07:28"
/// receiver : "?**"
/// receiverAddress : "???1209"
/// receiverCellphone : "1502379****"
/// receiverTitle : "??"
/// remark : ""
/// requestDatetime : "2015-12-15 13:07:28"
/// showName : "Love Radio ?? ?????? ????????"
/// showSchedule : "2016-01-09 19:30:00"
/// showScheduleSid : "eecfd0657fb445a7a36abedc9b621c89"
/// showSid : "52f30bbce4ef4122919cbc95c2f01c36"
/// sid : "21bc3cc65e9e47af952c1f4f1f0fd85a"
/// stateDesp : "???"
/// ticketPrice : 88000
/// ticketQuantity : 1
/// ticketSid : "c4583aa8a79a478e8e5cd14691028430"
/// totalPrice : 2300
/// tradeType : "EXPRESS"
/// userCellphone : "1502379****"
/// userLeaveMessage : ""
/// userSid : "a19cf6e3586143d283dd4128c456bfaf"
/// venueAddress : "?????????777?"
/// venueName : "????????"

class OrderBean {
  final int? bidding;
  final String? brokerAvatar;
  final String? brokerCellphone;
  final int? brokerDealNum;
  final String? brokerName;
  final String? brokerSid;
  final double? brokerStars;
  final String? code;
  final String? cover;
  final String? createdDatetime;
  final String? deliveryAddressSid;
  final int? deliveryFee;
  final int? evaluateStarts;
  final bool? isDelete;
  final bool? isSequential;
  final String? orderStatus;
  final List<OrderStatusArrayBean>? orderStatusArray;
  final String? orderStatusDesp;
  final String? orderType;
  final String? payType;
  final String? postTicketSid;
  final String? receiveDatetime;
  final String? receiver;
  final String? receiverAddress;
  final String? receiverCellphone;
  final String? receiverTitle;
  final String? remark;
  final String? requestDatetime;
  final String? showName;
  final String? showSchedule;
  final String? showScheduleSid;
  final String? showSid;
  final String? sid;
  final String? stateDesp;
  final int? ticketPrice;
  final int? ticketQuantity;
  final String? ticketSid;
  final int? totalPrice;
  final String? tradeType;
  final String? userCellphone;
  final String? userLeaveMessage;
  final String? userSid;
  final String? venueAddress;
  final String? venueName;

  OrderBean(this.bidding, this.brokerAvatar, this.brokerCellphone, this.brokerDealNum, this.brokerName, this.brokerSid, this.brokerStars, this.code, this.cover, this.createdDatetime, this.deliveryAddressSid, this.deliveryFee, this.evaluateStarts, this.isDelete, this.isSequential, this.orderStatus, this.orderStatusArray, this.orderStatusDesp, this.orderType, this.payType, this.postTicketSid, this.receiveDatetime, this.receiver, this.receiverAddress, this.receiverCellphone, this.receiverTitle, this.remark, this.requestDatetime, this.showName, this.showSchedule, this.showScheduleSid, this.showSid, this.sid, this.stateDesp, this.ticketPrice, this.ticketQuantity, this.ticketSid, this.totalPrice, this.tradeType, this.userCellphone, this.userLeaveMessage, this.userSid, this.venueAddress, this.venueName);

  static OrderBean fromMap(Map<String, dynamic> map) {
    OrderBean orderBean = OrderBean(
     map['bidding'],
     map['brokerAvatar'],
     map['brokerCellphone'],
     map['brokerDealNum'],
     map['brokerName'],
     map['brokerSid'],
     map['brokerStars'],
     map['code'],
     map['cover'],
     map['createdDatetime'],
     map['deliveryAddressSid'],
     map['deliveryFee'],
     map['evaluateStarts'],
     map['isDelete'],
     map['isSequential'],
     map['orderStatus'],
     map['orderStatusArray']!=null ? ([]..addAll(
       (map['orderStatusArray'] as List).map((o) => OrderStatusArrayBean.fromMap(o))
     )) : null,
     map['orderStatusDesp'],
     map['orderType'],
     map['payType'],
     map['postTicketSid'],
     map['receiveDatetime'],
     map['receiver'],
     map['receiverAddress'],
     map['receiverCellphone'],
     map['receiverTitle'],
     map['remark'],
     map['requestDatetime'],
     map['showName'],
     map['showSchedule'],
     map['showScheduleSid'],
     map['showSid'],
     map['sid'],
     map['stateDesp'],
     map['ticketPrice'],
     map['ticketQuantity'],
     map['ticketSid'],
     map['totalPrice'],
     map['tradeType'],
     map['userCellphone'],
     map['userLeaveMessage'],
     map['userSid'],
     map['venueAddress'],
     map['venueName'],
    );
    return orderBean;
  }

  Map toJson() => {
    "bidding": bidding,
    "brokerAvatar": brokerAvatar,
    "brokerCellphone": brokerCellphone,
    "brokerDealNum": brokerDealNum,
    "brokerName": brokerName,
    "brokerSid": brokerSid,
    "brokerStars": brokerStars,
    "code": code,
    "cover": cover,
    "createdDatetime": createdDatetime,
    "deliveryAddressSid": deliveryAddressSid,
    "deliveryFee": deliveryFee,
    "evaluateStarts": evaluateStarts,
    "isDelete": isDelete,
    "isSequential": isSequential,
    "orderStatus": orderStatus,
    "orderStatusArray": orderStatusArray?.map((o)=>o.toJson()).toList(growable: false),
    "orderStatusDesp": orderStatusDesp,
    "orderType": orderType,
    "payType": payType,
    "postTicketSid": postTicketSid,
    "receiveDatetime": receiveDatetime,
    "receiver": receiver,
    "receiverAddress": receiverAddress,
    "receiverCellphone": receiverCellphone,
    "receiverTitle": receiverTitle,
    "remark": remark,
    "requestDatetime": requestDatetime,
    "showName": showName,
    "showSchedule": showSchedule,
    "showScheduleSid": showScheduleSid,
    "showSid": showSid,
    "sid": sid,
    "stateDesp": stateDesp,
    "ticketPrice": ticketPrice,
    "ticketQuantity": ticketQuantity,
    "ticketSid": ticketSid,
    "totalPrice": totalPrice,
    "tradeType": tradeType,
    "userCellphone": userCellphone,
    "userLeaveMessage": userLeaveMessage,
    "userSid": userSid,
    "venueAddress": venueAddress,
    "venueName": venueName,
  }..removeWhere((k,v)=>v==null);
}

/// operateDatetime : "2015-12-15 13:07:28"
/// operateUserSid : "b82378ccaccb403c9d8420274372c904"
/// operateUsername : "?**"
/// orderSid : "21bc3cc65e9e47af952c1f4f1f0fd85a"
/// orderType : "1"
/// sid : "04940ed81540466ea4408f79989a5d54"
/// state : "TAKING"

class OrderStatusArrayBean {
  final String? operateDatetime;
  final String? operateUserSid;
  final String? operateUsername;
  final String? orderSid;
  final String? orderType;
  final String? sid;
  final String? state;

  OrderStatusArrayBean(this.operateDatetime, this.operateUserSid, this.operateUsername, this.orderSid, this.orderType, this.sid, this.state);

  static OrderStatusArrayBean fromMap(Map<String, dynamic> map) {
    OrderStatusArrayBean orderStatusArrayBean = OrderStatusArrayBean(
     map['operateDatetime'],
     map['operateUserSid'],
     map['operateUsername'],
     map['orderSid'],
     map['orderType'],
     map['sid'],
     map['state'],
    );
    return orderStatusArrayBean;
  }

  Map toJson() => {
    "operateDatetime": operateDatetime,
    "operateUserSid": operateUserSid,
    "operateUsername": operateUsername,
    "orderSid": orderSid,
    "orderType": orderType,
    "sid": sid,
    "state": state,
  }..removeWhere((k,v)=>v==null);
}

/// address : ""
/// receiver : "?"
/// receiverCellphone : "1388345****"
/// sid : "001190e19e754001b53701d0aa81bfe0"
/// sortNumber : 1
/// title : "??"
/// type : "FACETOFACE"
/// userSid : "a19cf6e3586143d283dd4128c456bfaf"

class FacetofaceAddressBean {
  final String? address;
  final String? receiver;
  final String? receiverCellphone;
  final String? sid;
  final int? sortNumber;
  final String? title;
  final String? type;
  final String? userSid;

  FacetofaceAddressBean(this.address, this.receiver, this.receiverCellphone, this.sid, this.sortNumber, this.title, this.type, this.userSid);

  static FacetofaceAddressBean fromMap(Map<String, dynamic> map) {
    FacetofaceAddressBean facetofaceAddressBean = FacetofaceAddressBean(
     map['address'],
     map['receiver'],
     map['receiverCellphone'],
     map['sid'],
     map['sortNumber'],
     map['title'],
     map['type'],
     map['userSid'],
    );
    return facetofaceAddressBean;
  }

  Map toJson() => {
    "address": address,
    "receiver": receiver,
    "receiverCellphone": receiverCellphone,
    "sid": sid,
    "sortNumber": sortNumber,
    "title": title,
    "type": type,
    "userSid": userSid,
  }..removeWhere((k,v)=>v==null);
}

/// address : "???1209"
/// createdDatetime : "2015-12-09 19:36:38"
/// receiver : "?**"
/// receiverCellphone : "1502379****"
/// sid : "c25c1954ca204dee8fc18f51bcc71a3e"
/// sortNumber : 1
/// title : "??"
/// type : "EXPRESS"
/// userSid : "a19cf6e3586143d283dd4128c456bfaf"

class ExpressAddressBean {
  final String? address;
  final String? createdDatetime;
  final String? receiver;
  final String? receiverCellphone;
  final String? sid;
  final int? sortNumber;
  final String? title;
  final String? type;
  final String? userSid;

  ExpressAddressBean(this.address, this.createdDatetime, this.receiver, this.receiverCellphone, this.sid, this.sortNumber, this.title, this.type, this.userSid);

  static ExpressAddressBean fromMap(Map<String, dynamic> map) {
    ExpressAddressBean expressAddressBean = ExpressAddressBean(
     map['address'],
     map['createdDatetime'],
     map['receiver'],
     map['receiverCellphone'],
     map['sid'],
     map['sortNumber'],
     map['title'],
     map['type'],
     map['userSid'],
    );
    return expressAddressBean;
  }

  Map toJson() => {
    "address": address,
    "createdDatetime": createdDatetime,
    "receiver": receiver,
    "receiverCellphone": receiverCellphone,
    "sid": sid,
    "sortNumber": sortNumber,
    "title": title,
    "type": type,
    "userSid": userSid,
  }..removeWhere((k,v)=>v==null);
}

/// a : 1
/// b : 2

class ObjectArrayBean {
  final int? a;
  final int? b;

  ObjectArrayBean(this.a, this.b);

  static ObjectArrayBean fromMap(Map<String, dynamic> map) {
    ObjectArrayBean objectArrayBean = ObjectArrayBean(
     map['a'],
     map['b'],
    );
    return objectArrayBean;
  }

  Map toJson() => {
    "a": a,
    "b": b,
  }..removeWhere((k,v)=>v==null);
}