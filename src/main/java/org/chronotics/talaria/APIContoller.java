package org.chronotics.talaria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIContoller {
	@RequestMapping("/hello")
	@ResponseBody
	public String home() {
	    return "Hello World!";
	}
}
