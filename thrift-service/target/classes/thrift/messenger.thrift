namespace cpp thrift_gen_messenger
namespace java thrift_gen_messenger
namespace php thrift_gen_messenger

struct Message {
	1: required string _sender_id;
	2: required string _receiver_id;
	3: required string _subject;
	4: required string _sequence_no;
	5: required bool _bool;
	6: required i16 _i16;
	7: required i32 _i32;
	8: required i64 _i64;
	9: required double _double;
	10: required string _string;
	11: required string _payload_string;
	12: optional string _payload_binary;
}

service TransferService {
	void writeMessageBegin(1:Message _v),
	void writeMessage(1:Message _v),
	void writeMessageEnd(),
	void writeBool(1:bool _v),
	void writeI16(1:i16 _v),
	void writeI32(1:i32 _v),
	void writeI64(1:i64 _v),
	void writeDouble(1:double _v),
	void writeString(1:string _v),
	Message readMessageBegin(1:string _receiver_id, 2:string _subject),
	Message readMessage(1:string _receiver_id, 2:string _subject),
	bool readMessageEnd(1:string _receiver_id, 2:string _subject),
	bool readBool(),
	i16 readI16(),
	i32 readI32(),
	i64 readI64(),
	double readDouble(),
	string readString()
}
