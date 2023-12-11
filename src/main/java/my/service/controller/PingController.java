package my.service.controller;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class PingController {

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public String ping() {
        JSONObject result = new JSONObject() {{
            put("pong", "Hello, World!");
        }};
        return result.toString();
    }
}
