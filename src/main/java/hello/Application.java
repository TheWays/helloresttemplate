package hello;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

//		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("10.1.26.201", 80));
//		requestFactory.setProxy(proxy);

		RestTemplate restTemplate = builder.build();
		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			logger.info(quote.toString());

			ResponseEntity<Quote[]> responseEntity = restTemplate
					.getForEntity("http://gturnquist-quoters.cfapps.io/api", Quote[].class);
			Quote[] quotes= responseEntity.getBody();
			logger.info(Arrays.toString(quotes));
			
		};
	}
}
