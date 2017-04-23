package rsvier;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import rsvier.product.Product;
import rsvier.product.ProductCategory;
import rsvier.product.ProductService;

@Controller
class WelcomeController {

    @Autowired
    ProductService productService;
    
	@RequestMapping("/")
	public String welcome(Model model) {
        List<Product> list = productService.getAllProducts();
        model.addAttribute("productlist", list);
		return "welcome";
	}
        
        @RequestMapping("/boerPiet")
	public String contact() {
		return "BoerPiet";
	}
        
        @RequestMapping("/Kaas")
	public String Keuze() {
		return "KaasKeuzeMenu";
	}

}
