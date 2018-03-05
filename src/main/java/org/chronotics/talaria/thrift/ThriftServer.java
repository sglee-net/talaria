package org.chronotics.talaria.thrift;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import org.chronotics.talaria.thrift.gen.TransferService;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

public class ThriftServer {			
	
	public static void startServer(
			TransferService.Iface _serviceHandler, 
			ThriftProperties _properties) {        
		try {
//			TransferService.Iface serviceHandler = null;		
//			if(_handler_type.equals(TransferServiceHandler.class.getName())) {
//				serviceHandler = new TransferServiceHandler();				
//			} else if(_handler_type.equals(
//					TransferServiceHandler.class.getName())) {
//				serviceHandler = new TransferServiceHandler();
//			} else {
//				serviceHandler = new TransferServiceHandler();
//			}
//			serviceHandler = new ThriftHandler();
			
			final TransferService.Processor<TransferService.Iface> processor = 
					new TransferService.Processor<TransferService.Iface>(_serviceHandler);
			
			Runnable server = new Runnable() {
				public void run() {
					server(processor, _properties);
				}
			};      
			
//			Runnable secure = new Runnable() {
//				public void run() {
//					secure(processor);
//				}
//			};
  	      
			new Thread(server).start();
			
//			new Thread(secure).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
    
	public static void server(
			TransferService.Processor<TransferService.Iface> processor, 
			ThriftProperties _properties) {
		try {			
			String ip = "192.168.0.41";//_properties.getServerIp();
			int port = 9091;//Integer.parseInt(_properties.getServerPort());
    		
			InetAddress listenAddress = InetAddress.getByName(ip);
			TServerTransport serverTransport = new TServerSocket(
    				new InetSocketAddress(listenAddress, port));
    		
    		//TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
    		
    		// Use this for a multithreaded server
    		TServer server = 
    				new TThreadPoolServer(
    						new TThreadPoolServer.Args(serverTransport).processor(processor));
    		System.out.println("Starting the simple server...");
    		server.serve();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public static void secure(
			TransferService.Processor<TransferService.Iface> processor, 
			ThriftProperties _properties) {
		int port = Integer.parseInt(_properties.getSecurePort());
		String keyStore = _properties.getSecureKeyStore();
		String keyPass = _properties.getSecureKeyPass();
		
		try {
			/*
			 * Use TSSLTransportParameters to setup the required SSL parameters. In this example
			 * we are setting the keystore and the keystore password. Other things like algorithms,
			 * cipher suites, client auth etc can be set. 
			 */
			TSSLTransportParameters params = new TSSLTransportParameters();
			// The Keystore contains the private key
			params.setKeyStore(keyStore, keyPass, null, null);
			
			/*
			 * Use any of the TSSLTransportFactory to get a server transport with the appropriate
			 * SSL configuration. You can use the default settings if properties are set in the command line.
			 * Ex: -Djavax.net.ssl.keyStore=.keystore and -Djavax.net.ssl.keyStorePassword=thrift
			 * 
			 * Note: You need not explicitly call open(). The underlying server socket is bound on return
			 * from the factory class. 
			 */
			TServerTransport serverTransport = TSSLTransportFactory.getServerSocket(port, 0, null, params);
			//TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			
			// Use this for a multi threaded server
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(serverTransport).processor(processor));
			System.out.println("Starting the secure server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
