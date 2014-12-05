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

	/** 
	 * Do a single call to the hsadmin api.
	 * @param args expects username and password
	 */
	public static void main(String[] args) {
		try {
			final String username = args[0];
			final String password = args[1];
			final TicketService ticketService = new TicketService(username, password);
			final String grantingTicket = ticketService.getGrantingTicket();
			
			final String ticket = ticketService.getServiceTicket(grantingTicket);
			
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
			
			final List<Serializable> xmlRpcParamsList = new ArrayList<Serializable>();
			xmlRpcParamsList.add(username);
			xmlRpcParamsList.add(ticket);
			final HashMap<String, Serializable> whereParamsMap = new HashMap<String, Serializable>();
			xmlRpcParamsList.add(whereParamsMap);
			final Object[] rpcResult = (Object[]) client.execute("emailaddress.search", xmlRpcParamsList);
			for (final Object resObject : rpcResult) {
				@SuppressWarnings("unchecked")
				final Map<String, Serializable> emailaddressData = (Map<String, Serializable>) resObject;
				System.out.print(emailaddressData.get("emailaddress") + " : ");
				final Object[] targets = (Object[]) emailaddressData.get("target");
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
