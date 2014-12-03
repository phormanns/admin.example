package net.hostsharing.admin.example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class Main {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		try {
			final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("https://config.hostsharing.net:443/hsar/xmlrpc/hsadmin"));
			config.setEnabledForExtensions(true);
			final XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object rpcResult = client.execute("system.listMethods", new ArrayList());
			System.out.println(rpcResult.getClass().getSimpleName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
