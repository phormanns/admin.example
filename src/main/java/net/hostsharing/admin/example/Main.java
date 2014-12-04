package net.hostsharing.admin.example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
			
//			Object rpcResult = client.execute("system.listMethods", new ArrayList());
//			Object[] resultArray = (Object[]) rpcResult;
//			for (Object obj : resultArray) {
//				System.out.println(obj);
//			}
			
			final ArrayList params = new ArrayList();
			params.add("peh18-anton");
			params.add("ST-27952-9BSgswicmBc2Mp6AWW0L-login.hostsharing.net");
			params.add(new HashMap());
			Object[] rpcResult = (Object[]) client.execute("emailaddress.search", params);
			for (Object resObject : rpcResult) {
				System.out.println(resObject.getClass().getName());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
