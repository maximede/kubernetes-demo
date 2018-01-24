package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@RestController
public class Application {

    @Value("${MY_VAR:my default variable}")
    private String myVariable;
    @Value("${MY_SECRET_VAR:my default secret variable}")
    private String mySecretVariable;
    @Value("${POD_NAME:}")
    private String podName;

    @RequestMapping("/")
    public String home() {
        return "<html><body>Hello Java! <br/>\nPod name is "+podName+"\n<br/>Env Variable is "+myVariable+ " <br/>\nsecret is "+mySecretVariable;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
