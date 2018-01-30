package net.sglee.websocket.client;

import com.fasterxml.jackson.databind.JsonNode;

import net.sglee.util.file.JsonParser;

public class AutomatedMessageHandler implements MessageHandler {

	public String handleTextMessage(String _msg) {
//		// TODO Auto-generated method stub		
//		JsonParser parser=new JsonParser();
//		JsonNode node=parser.parse(_msg);
//		JsonNode nodeSender=node.findValue("sender");
//		JsonNode nodeReceiver=node.findValue("receiver");
//		JsonNode nodeCommand=node.findValue("command");
//		JsonNode nodeCommandName=nodeCommand.findValue("name");
//		String commandName=nodeCommandName.textValue();
//		
//		String reply=null;
//		
//		CommandRepository commandRepository=(CommandRepository) CommandRepository.getInstance();
//		Command command=commandRepository.get(commandName);
//		if(command!=null) {
//			reply=command.run(nodeCommand);
//		}
//		
//		//System.out.println(_msg);
//		//String reply=new String("client, time: "+System.currentTimeMillis());
//		return reply;
//		MessageQueue = 
		return "";
	}

	public String handleBynaryMessage(byte[] _bytes, long _offset, long _length) {
		// TODO Auto-generated method stub
		return null;
	}

}

//public class MessageHandler {
////public abstract String handleTextMessage(String _msg);
////public abstract String handleBynaryMessage(byte[] _bytes,long _offset,long _length);
//
//public String handleTextMessage(String _msg) {
//return new String("client message");
//
////// TODO Auto-generated method stub		
////JsonParser parser=new JsonParser();
////JsonNode node=parser.parse(_msg);
////JsonNode nodeSender=node.findValue("sender");
////JsonNode nodeReceiver=node.findValue("receiver");
////JsonNode nodeCommand=node.findValue("command");
////JsonNode nodeCommandName=nodeCommand.findValue("name");
////String commandName=nodeCommandName.textValue();
////
////String reply=null;
////
////CommandRepository commandRepository=(CommandRepository) CommandRepository.getInstance();
////Command command=commandRepository.get(commandName);
////if(command!=null) {
////	reply=command.run(nodeCommand);
////}
////
//////System.out.println(_msg);
//////String reply=new String("client, time: "+System.currentTimeMillis());
////return reply;
//}
//
//public String handleBynaryMessage(byte[] _bytes, long _offset, long _length) {
//// TODO Auto-generated method stub
//String reply=null;//new String("client, time: "+System.currentTimeMillis());
//return reply;
//}
//}