package com.hostel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping(value = { "/error" })
	public ModelAndView errorPage(HttpServletRequest request, @RequestParam(name = "message") String message) {
		ModelAndView model = new ModelAndView();
		return model;
	}
}
