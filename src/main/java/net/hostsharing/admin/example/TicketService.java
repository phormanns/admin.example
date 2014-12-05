package net.hostsharing.admin.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Helper for service tickets.
 * Hostsharing uses the CAS authentication service to authenticate 
 * users of hostsharing services. This class is used to create a 
 * "ticket granting ticket" for a session and service ticket for 
 * individual service calls. 
 */
public class TicketService {

	final String user;
	final String password;

	public TicketService(final String user, final String password) {
		this.user = user;
		this.password = password;
	}

	public String getGrantingTicket() {
		String ticket = null;
		try {
			String userParam = "username=" + URLEncoder.encode(user, "UTF-8");
			String passwordParam = "password=" + URLEncoder.encode(password, "UTF-8");
			String encodedData = userParam + "&" + passwordParam;
			URL url = new URL("https://login.hostsharing.net/cas/v1/tickets");

			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			writer.write(encodedData);
			writer.close();
			connection.connect();
			ticket = connection.getHeaderField("Location");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ticket;
	}

	public String getServiceTicket(String grantingTicket) {
		String ticket = null;
		try {
			String serviceParam = "service=" + URLEncoder.encode("https://config.hostsharing.net:443/hsar/backend", "UTF-8");
			URL url = new URL(grantingTicket);

			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			writer.write(serviceParam);
			writer.close();
			connection.connect();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			ticket = reader.readLine();
			String readLine = reader.readLine();
			do {
				readLine = reader.readLine();
			} while (readLine != null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ticket;
	}

}
