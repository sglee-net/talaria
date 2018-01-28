package net.sglee.web_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/")
//public class Greeting extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//		throws ServletException, IOException {
//		response.setContentType("text/plain");
//		response.getWriter().write("Hello");
//	}
//}

@Controller
public class Greeting {
	@RequestMapping(path={"/hello"},method=RequestMethod.GET)
	public String sayHello(Model model) {
//		model.addAttribute("message","Hello Spring MVC!");
		return "chart";
	}
}