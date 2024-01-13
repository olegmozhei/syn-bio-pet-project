package my.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
// We should use direct @Import instead of @ComponentScan to speed up cold starts
@ComponentScan(basePackages = "my.service")
//@Import({PingController.class, QuizController.class})
@EntityScan("my.service")
public class Application {

    public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }
}