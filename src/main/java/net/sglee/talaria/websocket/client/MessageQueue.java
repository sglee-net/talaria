package net.sglee.talaria.websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import net.sglee.automation.jobcontrol.JobManager;
import net.sglee.util.collection.GenericConcurrentLinkedQueue;

public class MessageQueue extends GenericConcurrentLinkedQueue<Message> {
	private static final Logger logger = LoggerFactory.getLogger(MessageQueue.class);
	
	private MessageHandler messageHandler = null;
	public void setMessageHandler(MessageHandler _handler) {
		messageHandler = _handler;
	}
	
	@SuppressWarnings("unused")
	private MessageQueue() {}
	
	private String id=null;
	public String getId() {
		return id;
	}

//	public void setId(String id) {
//		this.id = id;
//	}

	public MessageQueue(String _id) {
		id=new String(_id);
	}
	
//	JobManager jobManager=null;
//	
//	public JobManager getJobManager() {
//		return jobManager;
//	}
//
//	public void setJobManager(JobManager jobManager) {
//		this.jobManager = jobManager;
//	}
	

	@Override
	public boolean add(Message _e){
		notifyUpdate();
		return super.add(_e);
	}
	
	private synchronized void notifyUpdate() {
		Message message=this.poll(); // this.peak();
		if(message==null) {
			return;
		}
		
		if(messageHandler != null) {
			messageHandler.executeMessages(this);
		}
////		for(Job job : messageHandler.values()) {
////			job.receiveObject(message);
////		}
////		if(job!=null) {
////			job.receiveObject(message);
////		}
//		if(jobManager!=null) {
//			if(!jobManager.getRootJob().isRunning()) {
//				new Thread(jobManager).start();
//				logger.info("JobManager starts.");
//			} else {
//				jobManager.getRootJob().receiveObject(message);
//				logger.info("RootJob of JobManager received the message, "+ message.getPayload());
//			}
//		}
		
		// do something... 
		System.out.println(message);
	}
}
