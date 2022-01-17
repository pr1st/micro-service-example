package app.gateway;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatewayIndexController {
    @GetMapping("/")
    public Model index() {
        return null;
    }
}
