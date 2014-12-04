package net.hostsharing.admin.example;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class Main {

	public static void main(String[] args) {
		try {
			TicketService ticketService = new TicketService(args[0], args[1]);
			String grantingTicket = ticketService.getGrantingTicket();
			
			String ticket = ticketService.getServiceTicket(grantingTicket);
			
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
			
			final List<Serializable> params = new ArrayList<Serializable>();
			params.add(args[0]);
			params.add(ticket);
			params.add(new HashMap<Object, Object>());
			Object[] rpcResult = (Object[]) client.execute("emailaddress.search", params);
			for (Object resObject : rpcResult) {
				@SuppressWarnings("unchecked")
				Map<String, Serializable> emailaddressData = (Map<String, Serializable>) resObject;
				System.out.print(emailaddressData.get("emailaddress") + " : ");
				Object[] targets = (Object[]) emailaddressData.get("target");
				for (Object target : targets) {
					System.out.print(target + ",");
				}
				System.out.println();
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
