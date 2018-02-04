package net.sglee.websocket.common;

public class RequestInformation {
	private long firstRequestedTime;
	private String name;
	public boolean isEqual(RequestInformation _arg) {
		return (this.firstRequestedTime == _arg.firstRequestedTime &&
				this.name == _arg.name)? true : false;
	}
}
