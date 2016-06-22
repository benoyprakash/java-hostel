package com.hostel.service.impl;

import static com.hostel.utils.ViewConstants.HOME_VIEW;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.hostel.service.HomeService;

@Service(value = "homeService")
public class HomeServiceImpl implements HomeService {

	@Override
	public ModelAndView homePage(HttpServletRequest request) {

		return new ModelAndView("/WEB-INF/jsp/" + HOME_VIEW);
	}

}