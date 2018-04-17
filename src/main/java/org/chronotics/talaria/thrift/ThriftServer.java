package org.chronotics.talaria.thrift;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import org.chronotics.talaria.thrift.gen.TransferService;
import org.springframework.beans.factory.annotation.Value;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

public class ThriftServer {
	
	public static void startServer(
			TransferService.Iface _serviceHandler, 
			ThriftServerProperties _properties) {        
		try {			
			final TransferService.Processor<TransferService.Iface> processor = 
					new TransferService.Processor<TransferService.Iface>(_serviceHandler);
			
			Runnable server = new Runnable() {
				public void run() {
					server(processor, _properties);
				}
			};      	      
			new Thread(server).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
    
	public static void server(
			TransferService.Processor<TransferService.Iface> processor, 
			ThriftServerProperties _properties) {
		try {			
			String ip = _properties.getIp(); //"192.168.0.41";//
			int port = Integer.parseInt(_properties.getPort()); //9091;//
    		
			InetAddress listenAddress = InetAddress.getByName(ip);
			TServerTransport serverTransport = new TServerSocket(
    				new InetSocketAddress(listenAddress, port));
    		// Simple server
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
