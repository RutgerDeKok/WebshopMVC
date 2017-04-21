package rsvier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
class WelcomeController {

	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}
        
        @RequestMapping("/boerPiet")
	public String contact() {
		return "BoerPiet";
	}

}
