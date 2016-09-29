package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dabpessoa.http.AcceptOption;
import me.dabpessoa.http.HTTPConnection;
import me.dabpessoa.http.HTTPMethods;
import me.dabpessoa.http.ProxyCredentials;

public class Main {

	public static void main(String[] args) throws MalformedURLException, IOException {
	
		ProxyCredentials proxyCredentials = new ProxyCredentials("proxy", 8080, "dabpessoa", "senha");
		
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Accept", Arrays.asList(AcceptOption.HTML.getMimeTypeString()));
		
//		HTTPConnection connection = new HTTPConnection("https://www.google.com", HTTPMethods.GET.name(), proxyCredentials, headers);
//		HTTPConnection connection = new HTTPConnection("http://www.bol.uol.com.br/", HTTPMethods.GET.name(), proxyCredentials, headers);
		HTTPConnection connection = new HTTPConnection("http://www.imdb.com", HTTPMethods.GET.name(), proxyCredentials, headers);
		connection.addPath("/find");
		connection.addParam("ref_", "nv_sr_fn");
		connection.addParam("q", "batman");
		connection.addParam("s", "all");
		
		String response = connection.request();
		
		System.out.println(response);
		
	}
	
}
