package com.hostel.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.hostel.configuration.ApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SuiteClasses({ CustomerControllerTest.class })
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class AllTests {

}
