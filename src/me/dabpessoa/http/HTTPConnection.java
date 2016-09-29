package me.dabpessoa.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HTTPConnection {
	
	private String url;
	private String method;
	private Map<String, List<String>> headers;
	private Map<String, String> urlParams;
	private ProxyCredentials proxyCredentials;
	private HttpURLConnection connection;
	
	public HTTPConnection(String url, String method) {
		this.url = url;
		this.method = method;
	}
	
	public HTTPConnection(String url, String method, ProxyCredentials proxyCredentials) {
		this.url = url;
		this.method = method;
		this.proxyCredentials = proxyCredentials;
	}
	
	public HTTPConnection(String url, String method, ProxyCredentials proxyCredentials, Map<String, List<String>> headers) {
		this.url = url;
		this.method = method;
		this.proxyCredentials = proxyCredentials;
		this.headers = headers;
	}
	
	public void addPath(String path) {
		if (url != null) {
			url += path;
		} else url = path;
	}
	
	public void addParam(String name, String value) {
		if (getUrlParams() == null) {
			setUrlParams(new HashMap<String,String>());
		}
		getUrlParams().put(name, value);
	}
	
	public String request() throws IOException {
		
		prepareConnection();
		connect();
		
		if (connection.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ connection.getResponseCode());
		}

		DataInputStream dis = new DataInputStream(connection.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(dis, "UTF-8"));

		StringBuffer response = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			response.append(line);
		}
		
		disconnect();
		
		return response.toString();
		
	}
	
	public Proxy createHTTPProxy(String proxyHost, Integer proxyPort) {
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
	}
	
	public void setChunkedStreamingMode(int chunklen) {
		if (connection != null) connection.setChunkedStreamingMode(chunklen);
	}
	
	public void setDoOutput(boolean value) {
		if (connection != null) connection.setDoOutput(value);
	}
	
	private void prepareConnection() throws MalformedURLException, IOException {
		if (connection == null) {
			
			url = verifyURL(url);
			
			connection = (HttpURLConnection) new URL(url).openConnection(proxyCredentials != null ? createHTTPProxy(proxyCredentials.getHost(), proxyCredentials.getPort()) : Proxy.NO_PROXY);
			
			if (proxyCredentials != null && proxyCredentials.getUsername() != null && proxyCredentials.getPassword() != null) {
				String uname_pwd = proxyCredentials.getUsername() + ":" + proxyCredentials.getPassword();
				String authString = "Basic " + Base64.getEncoder().encodeToString(uname_pwd.getBytes());
				connection.setRequestProperty("Proxy-Authorization", authString);
			}
			
			connection.setRequestMethod(method);
			if (headers != null) {
				for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
					List<String> value =  entry.getValue();
					StringBuffer valueBuffer = new StringBuffer();
					for (int i = 0 ; i < value.size() ; i++) {
						if (i+1 == value.size()) valueBuffer.append(value.get(i));
						else valueBuffer.append(value.get(i)+",");
					}
					connection.setRequestProperty(entry.getKey(), valueBuffer.toString());
				}
			}
			
			if (getMethod() != null && method.equalsIgnoreCase(HTTPMethods.POST.name())) {
				connection.setChunkedStreamingMode(0);
				connection.setDoOutput(true);
			}
			
		}
	}

	private void connect() throws IOException {
		if (connection != null) connection.connect();
	}
	
	private void disconnect() {
		if (connection != null) connection.disconnect();
	}
	
	private String verifyURL(String url) {
		StringBuffer newURL = new StringBuffer(url);
		if (newURL.indexOf("?") == -1) {
			newURL.append("?");
		} else if (newURL.indexOf("?") != newURL.length()-1) {
			newURL.append("&");
		}
		
		if (urlParams != null) {
			Iterator<String> it = urlParams.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = urlParams.get(key);
				value = value.replace(" ", "%20");
				newURL.append(key+"="+value);
				if (it.hasNext()) newURL.append("&");
			}
		}
		
		return newURL.toString();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	
	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}
	
	public Map<String, String> getUrlParams() {
		return urlParams;
	}
	
	public void setUrlParams(Map<String, String> urlParams) {
		this.urlParams = urlParams;
	}
	
	public ProxyCredentials getProxyCredentials() {
		return proxyCredentials;
	}
	
	public void setProxyCredentials(ProxyCredentials proxyCredentials) {
		this.proxyCredentials = proxyCredentials;
	}
	
	public HttpURLConnection getConnection() {
		return connection;
	}
	
	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}
	
}
