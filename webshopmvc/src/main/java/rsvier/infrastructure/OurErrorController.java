package rsvier.infrastructure;


import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



    
@Controller
public class OurErrorController implements ErrorController {
 
    @RequestMapping("/error")
    public String renderErrorPage(HttpServletRequest httpRequest, Model model) {
     
        String errorMsg = "";
       
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default:  {
                errorMsg = "Http Error Code: Een onbekende. Jij crashte het systeem!";
                break;
            }
        }
        model.addAttribute("errorMsg", errorMsg);
        return "error_page";
    }
     //geen verschil met de yesterday versie.
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "redirect:/error";
    }
}


