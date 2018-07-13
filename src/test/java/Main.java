import me.dabpessoa.http.AcceptOption;
import me.dabpessoa.http.HTTPConnection;
import me.dabpessoa.http.HTTPMethods;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
	
//		ProxyCredentials proxyCredentials = new ProxyCredentials("proxy", 8080, "dabpessoa", "senha");
//
//		Map<String, List<String>> headers = new HashMap<String, List<String>>();
//		headers.put("Accept", Arrays.asList(AcceptOption.HTML.getMimeTypeString()));
//
////		HTTPConnection connection = new HTTPConnection("https://www.google.com", HTTPMethods.GET.name(), proxyCredentials, headers);
////		HTTPConnection connection = new HTTPConnection("http://www.bol.uol.com.br/", HTTPMethods.GET.name(), proxyCredentials, headers);
//		HTTPConnection connection = new HTTPConnection("http://www.imdb.com", HTTPMethods.GET.name(), proxyCredentials, headers);
//		connection.addPath("/find");
//		connection.addParam("ref_", "nv_sr_fn");
//		connection.addParam("q", "batman");
//		connection.addParam("s", "all");
//
//		String response = connection.request();
//
//		System.out.println(response);


		// TESTE payU


//		ProxyCredentials proxyCredentials = new ProxyCredentials("proxy", 8080, "dabpessoa", "senha");

//		StringBuilder jsonRequestBuilder = new StringBuilder();
//		jsonRequestBuilder.append("{ ");
//		jsonRequestBuilder.append("	\"test\":false, ");
//		jsonRequestBuilder.append("	\"language\":\"en\", ");
//		jsonRequestBuilder.append("	\"command\":\"PING\", ");
//		jsonRequestBuilder.append("	\"merchant\":{ ");
//		jsonRequestBuilder.append("		\"apiLogin\":\"pRRXKOl8ikMmt9u\", ");
//		jsonRequestBuilder.append("		\"apiKey\":\"4Vj8eK4rloUd272L48hsrarnUA\" ");
//		jsonRequestBuilder.append("	} ");
//		jsonRequestBuilder.append("} ");
//
//		Map<String, List<String>> headers = new HashMap<String, List<String>>();
//		headers.put("Content-Type", Arrays.asList("application/json; charset=utf-8"));
//		headers.put("Accept", Arrays.asList(AcceptOption.JSON.getMimeTypeString()));
//		headers.put("Accept-Charset", Arrays.asList("utf-8"));
//		headers.put("Content-Length", Arrays.asList(Integer.toString(jsonRequestBuilder.toString().length())));
//
//		String url = "https://sandbox.api.payulatam.com/reports-api/4.0/service.cgi";
//		String method = HTTPMethods.POST.name();
//
//		HTTPConnection connection = new HTTPConnection(url, method, headers);
//
//		String response = connection.request(jsonRequestBuilder.toString().getBytes(StandardCharsets.UTF_8));
//
//		System.out.println(response);


//		String url = "http://localhost:8080/boletoglobal/api/teste/something";
		String url = "http://localhost:8080/boletoglobal/api/titulo/add";
		String requestMethod = HTTPMethods.PUT.name();

		StringBuilder jsonRequestBuilder = new StringBuilder();
		jsonRequestBuilder.append("{\"nome\":\"diego\",\"numero\":\"10\"}");
//		jsonRequestBuilder.append("{ ");
//		jsonRequestBuilder.append("	\"test\":false, ");
//		jsonRequestBuilder.append("	\"language\":\"en\", ");
//		jsonRequestBuilder.append("	\"command\":\"PING\", ");
//		jsonRequestBuilder.append("	\"merchant\":{ ");
//		jsonRequestBuilder.append("		\"apiLogin\":\"pRRXKOl8ikMmt9u\", ");
//		jsonRequestBuilder.append("		\"apiKey\":\"4Vj8eK4rloUd272L48hsrarnUA\" ");
//		jsonRequestBuilder.append("	} ");
//		jsonRequestBuilder.append("} ");

		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Content-Type", Arrays.asList("application/json; charset=utf-8"));
		headers.put("Accept", Arrays.asList(AcceptOption.JSON.getMimeTypeString()));
		headers.put("Accept-Charset", Arrays.asList("utf-8"));
		headers.put("Content-Length", Arrays.asList(Integer.toString(jsonRequestBuilder.toString().length())));

		// TOKEN
		headers.put("token", Arrays.asList("meuTokenDeTeste"));

		HTTPConnection connection = new HTTPConnection(url, requestMethod, headers);

		String response = null;
		try {
			connection.prepareConnection();
			connection.writeToRequestBody(jsonRequestBuilder.toString().getBytes(StandardCharsets.UTF_8));
			response = connection.request();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(response);


	}
	
}
