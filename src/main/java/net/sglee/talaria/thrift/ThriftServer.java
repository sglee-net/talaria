package net.sglee.talaria.thrift;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import net.sglee.talaria.thriftgen.TransferService;

import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.springframework.beans.factory.annotation.Autowired;

public class ThriftServer {	
	@Autowired
	private static ThriftProperties properties;
	
//	@PreDestroy
//    public static void stop() {
//		FactoryTServerTransport.getInstance().close();
//		FactoryTTransport.getInstance().close();
//    }
	
	public static void startServer(String _handler_type) {        
		try {
			TransferService.Iface serviceHandler = null;
			
			if(_handler_type.equals(TransferServiceHandler.class.getName())) {
				serviceHandler = new TransferServiceHandler();				
			} else if(_handler_type.equals(
					TransferServiceHandlerWS.class.getName())) {
				serviceHandler = new TransferServiceHandlerWS();
			} else {
				serviceHandler = new TransferServiceHandler();
			}
						
			final TransferService.Processor<TransferService.Iface> processor = 
					new TransferService.Processor<TransferService.Iface>(serviceHandler);
			
			Runnable server = new Runnable() {
				public void run() {
					server(processor);
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
			TransferService.Processor<TransferService.Iface> processor) {
		try {			
			String ip = properties.getServerIp();
			int port = Integer.parseInt(properties.getServerPort());
    		
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
			TransferService.Processor<TransferService.Iface> processor) {
		int port = Integer.parseInt(properties.getSecurePort());
		String keyStore = properties.getSecureKeyStore();
		String keyPass = properties.getSecureKeyPass();
		
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
