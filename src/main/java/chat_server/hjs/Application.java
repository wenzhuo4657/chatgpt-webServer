package chat_server.hjs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: main
 * @author: wenzhuo4657
 * @date: 2024/5/15 20:05
 * @Version: 1.0
 * @description:
 */
@SpringBootApplication
@RestController
public class Application {
        private Logger log= LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/verify")
    public ResponseEntity<String> verify(String token) {
        log.info("验证 token：{}", token);
        return ResponseEntity.status(HttpStatus.OK).body("verify success!");
    }
    @RequestMapping("/success")
    public String success(){
        return "test success by xfg";
    }

}