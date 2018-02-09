package net.sglee.talaria.conn.websocket;

public class RequestInformation {
	private long firstRequestedTime;
	private String name;
	private String userId;
//	private Session sesseion;
	public boolean isEqual(RequestInformation _arg) {
		return (this.firstRequestedTime == _arg.firstRequestedTime &&
				this.name == _arg.name)? true : false;
	}
}
