package net.hostsharing.admin.example;

import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;


public interface IRemote {

		public abstract List<Map<String, Object>> search(
				String runAsUser,
				String ticket, 
				Map<String, String> whereParams
			) throws XmlRpcException;

		public abstract Map<String, Object> add(
				String runAsUser, 
				String ticket,
				Map<String, Object> setParams
			) throws XmlRpcException;

		public abstract void delete(
				String runAsUser, 
				String ticket,
				Map<String, String> whereParams
			) throws XmlRpcException;

		public abstract List<Map<String, Object>> update(
				String runAsUser,
				String ticket, 
				Map<String, Object> setParams,
				Map<String, String> whereParams
			) throws XmlRpcException;

}
