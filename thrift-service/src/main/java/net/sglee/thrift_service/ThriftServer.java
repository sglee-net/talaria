package net.sglee.thrift_service;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import javax.annotation.PreDestroy;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import thrift_gen_messenger.TransferService;

public class ThriftServer {	
	public static TransferServiceHandler handler;
	public static TransferService.Processor processor;
	
//	@PreDestroy
//    public static void stop() {
//		FactoryTServerTransport.getInstance().close();
//		FactoryTTransport.getInstance().close();
//    }
	
	public static void startServer() {        
		try {
  	      handler = new TransferServiceHandler();
  	      processor = new TransferService.Processor(handler);

  	      Runnable server = new Runnable() {
  	    	  public void run() {
  	    		  server(processor);
  	    	  }
  	      };      
//  	      Runnable secure = new Runnable() {
//  	    	  public void run() {
//  	    		  secure(processor);
//  	    	  }
//  	      };
  	      
  	      new Thread(server).start();
  	      //new Thread(secure).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
    
	public static void server(TransferService.Processor processor) {
		try {
			InetAddress listenAddress = InetAddress.getByName("192.168.0.41");//getBindAddress(conf);
			int port = 9091;
    		TServerTransport serverTransport = new TServerSocket(
    				new InetSocketAddress(listenAddress, port));
    		
    		//TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
    		
    		// Use this for a multithreaded server
    		TServer server = 
    				new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
    		System.out.println("Starting the simple server...");
    		server.serve();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public static void secure(TransferService.Processor processor) {
		try {
			/*
			 * Use TSSLTransportParameters to setup the required SSL parameters. In this example
			 * we are setting the keystore and the keystore password. Other things like algorithms,
			 * cipher suites, client auth etc can be set. 
			 */
			TSSLTransportParameters params = new TSSLTransportParameters();
			// The Keystore contains the private key
			params.setKeyStore("~/.keystore", "thrift", null, null);
			
			int port_secure = 9092;
			/*
			 * Use any of the TSSLTransportFactory to get a server transport with the appropriate
			 * SSL configuration. You can use the default settings if properties are set in the command line.
			 * Ex: -Djavax.net.ssl.keyStore=.keystore and -Djavax.net.ssl.keyStorePassword=thrift
			 * 
			 * Note: You need not explicitly call open(). The underlying server socket is bound on return
			 * from the factory class. 
			 */
			TServerTransport serverTransport = TSSLTransportFactory.getServerSocket(port_secure, 0, null, params);
			//TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			
			// Use this for a multi threaded server
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			System.out.println("Starting the secure server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
