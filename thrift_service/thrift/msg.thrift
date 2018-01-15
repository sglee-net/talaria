namespace cpp thrift_msg
namespace java thrift_msg
namespace php thrift_msg

struct Msg {
	1: required i32 _id;
	2: required string _name;
	3: string _msg;
	4: optional list<binary> _list;
	5: optional map<string,binary> _map;
	6: optional binary _value;
}

service TransferService {
	Msg getMsg()
}
