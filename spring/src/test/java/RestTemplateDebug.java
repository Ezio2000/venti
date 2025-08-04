import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

void main() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("1", "2"));
    Object res = restTemplate.getForObject("http://localhost:23000/baseconfig/operation/v1/businessCity/1", Object.class);
    System.out.println(res);
}
