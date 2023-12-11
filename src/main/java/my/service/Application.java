package my.service;

import my.service.controller.QuizController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import my.service.controller.PingController;


@SpringBootApplication
// We should use direct @Import instead of @ComponentScan to speed up cold starts
@ComponentScan(basePackages = "my.service")
//@Import({PingController.class, QuizController.class})
public class Application {

    public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }
}