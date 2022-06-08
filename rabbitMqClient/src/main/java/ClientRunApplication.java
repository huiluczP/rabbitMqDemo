import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@SpringBootApplication
@ComponentScan({"com.huiluczp"})
public class ClientRunApplication {
    public static void main(String[] args){
        SpringApplication.run(ClientRunApplication.class, args);
    }
}
