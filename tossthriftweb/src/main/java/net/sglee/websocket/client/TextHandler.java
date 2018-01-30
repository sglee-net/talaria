package net.sglee.websocket.client;

import com.fasterxml.jackson.databind.JsonNode;
import net.sglee.util.file.JsonParser;

public class TextHandler implements MessageHandler {

	@Override
	public String handleTextMessage(String _msg) {
//		JsonParser parser=new JsonParser();
//		JsonNode node=parser.parse(_msg);
//		JsonNode nodeSender=node.findValue("sender");
//		JsonNode nodeReceiver=node.findValue("receiver");
//		JsonNode nodeCommand=node.findValue("command");
//		JsonNode nodeCommandName=nodeCommand.findValue("name");
//		String commandName=nodeCommandName.textValue();
		
//		String reply=null;
//		
//		CommandRepository commandRepository=(CommandRepository) CommandRepository.getInstance();
//		Command command=commandRepository.get(commandName);
//		if(command!=null) {
//			reply=command.run(nodeCommand);
//		}
		
		//System.out.println(_msg);
		String reply=new String("client, time: "+System.currentTimeMillis());
		return reply;
	}

	@Override
	public String handleBynaryMessage(byte[] _bytes, long _offset, long _length) {
		// TODO Auto-generated method stub
		return null;
	}

}
