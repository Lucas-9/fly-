package top.lucas9.lblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lucas
 */
@SpringBootApplication
@EnableScheduling
public class LblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LblogApplication.class, args);
        System.out.println("http://localhost:8181/doc.html");
    }

}
