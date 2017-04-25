package rsvier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@RestController
public class ManualTestController {
	@Autowired
	private ThymeleafViewResolver resolver;

	@RequestMapping(value = "/manualtest", method = RequestMethod.GET)
	public View greetingForm(Model model) throws Exception {

		
		List<String> testList = Arrays.asList(new String[] {"een","twee","drie"});
		String[] environmentList = new String[] {"hot","cold","medium","sub"};

		model.addAttribute("testList", testList);
		model.addAttribute("source", new Source());
		model.addAttribute("environmentList", environmentList);
		return resolver.resolveViewName("manualtest", Locale.US);
	}

	@RequestMapping(value = "/manualtest", method = RequestMethod.POST)
	public String greetingSubmit(@ModelAttribute Source source, Model model) {
		System.out.println(source.getSourceName());
		for (String str : source.getTestList()) {
			System.out.println(str);
		}
		model.addAttribute("source", source);
		return "result";
	}

}