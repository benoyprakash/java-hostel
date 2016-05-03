package com.hostel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hostel.service.HomeService;

/**
 * @author Benoy
 *
 */
@RestController
public class HomeController {

	@Autowired
	@Qualifier(value = "homeService")
	private HomeService homeService;

	@RequestMapping(value = { "/", "/home" })
	public ModelAndView homePage(HttpServletRequest request) {

		return homeService.homePage(request);
	}
}