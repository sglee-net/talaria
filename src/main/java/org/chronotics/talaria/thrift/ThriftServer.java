package org.chronotics.talaria.thrift;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import org.chronotics.talaria.thrift.gen.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

public class ThriftServer {
	private static final Logger logger = 
			LoggerFactory.getLogger(ThriftServer.class);
	
	private static TransferService.Processor<TransferService.Iface> processor = null;
	private static TServer server = null;
	private static TServerTransport serverTransport = null;
	private static Runnable runnable = null;
	
	public static synchronized void startServer(
			TransferService.Iface _serviceHandler, 
			ThriftServerProperties _properties) {
		if(server != null) {
			if(!server.isServing()) {
				server.serve();
				logger.info("TServer is restarted");
			}
			logger.info("TServer is already started");
			return;
		}
		
		try {
			if(processor == null) {
				processor = new TransferService
						.Processor<TransferService.Iface>(_serviceHandler);
			}
			
			if(runnable == null) {
				runnable = new Runnable() {
					public void run() {
						server(processor, _properties);
					}
				};
				
				new Thread(runnable).start();
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public static synchronized void stopServer() {
		if(server != null) {
			if(server.isServing()) {
				logger.info("TServer is stopped");
				server.stop();
			} else {
				logger.info("TServer is already stopped");
			}
		}
	}
   
	private static void server(
			TransferService.Processor<TransferService.Iface> processor, 
			ThriftServerProperties _properties) {
		try {			
			String ip = _properties.getIp(); //"192.168.0.41";//
			int port = Integer.parseInt(_properties.getPort()); //9091;//
    		
			InetAddress listenAddress = InetAddress.getByName(ip);
			if(serverTransport == null) {
				serverTransport = new TServerSocket(
    							new InetSocketAddress(listenAddress, port));
			}
    		// Simple server
    		// TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
    		// Use this for a multithreaded server
			if(server == null) {
				server = new TThreadPoolServer(
							new TThreadPoolServer
							.Args(serverTransport)
							.processor(processor));
			}
    		
    		if(!server.isServing()) {
    			server.serve();
    		}
			
			logger.info("Starting the TThreadPoolServer server...");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private static void secure(
			TransferService.Processor<TransferService.Iface> processor, 
			ThriftServerProperties _properties) {
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
			if(serverTransport == null) {
				serverTransport = 
						TSSLTransportFactory
						.getServerSocket(port, 0, null, params);
			}
			
			//TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			// Use this for a multi threaded server
			if(server == null) {
				server = new TThreadPoolServer(
							new TThreadPoolServer
							.Args(serverTransport)
							.processor(processor));
			}
				
			if(!server.isServing()) {
    			server.serve();
    		}
				
			logger.info("Starting the TThreadPoolServer server...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
