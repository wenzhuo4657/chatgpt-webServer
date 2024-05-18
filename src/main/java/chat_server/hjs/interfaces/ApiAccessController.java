package chat_server.hjs.interfaces;

import chat_server.hjs.domain.security.service.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: ApiAccessController
 * @author: wenzhuo4657
 * @date: 2024/5/18 17:02
 * @Version: 1.0
 * @description:
 */

@RestController()
@RequestMapping("/api")
public class ApiAccessController  {
    private Logger log= LoggerFactory.getLogger(ApiAccessController.class);

        /**
           *  des: 根据用户名密码生成jwt 并返回注入shiro的实体类返回
           * */
    @RequestMapping("/authorize")
    public ResponseEntity<Map<String, String>> authorize(String username, String password) {
//     1，生成token
        JwtUtil jwtUtil=JwtUtil.DefaultJwtUtil();
        Map<String,Object> chaim=new HashMap<>();
        chaim.put("username",username);
        String token=jwtUtil.encode(username,5*60*1000,chaim);

//        2,封装响应体并返回
        Map<String ,String>map=new HashMap<>();
        map.put("msg", "授权成功");
        map.put("token", token);
        return ResponseEntity.ok(map);
    }



}