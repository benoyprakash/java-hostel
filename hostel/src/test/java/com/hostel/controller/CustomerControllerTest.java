/**
 * 
 */
package com.hostel.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.hostel.configuration.ApplicationConfiguration;

/**
 * @author Binu
 *
 */
@ActiveProfiles(value = {"test"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class CustomerControllerTest {

	@Autowired
	private CustomerController customerController;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.hostel.controller.CustomerController#getCustomer(javax.servlet.http.HttpServletRequest, java.lang.Long)}
	 * .
	 */
	@Test
	public void testGetCustomer() {
		customerController.getCustomer(null, 1L);
	}

	/**
	 * Test method for
	 * {@link com.hostel.controller.CustomerController#saveCustomer(javax.servlet.http.HttpServletRequest)}
	 * .
	 */
	@Test
	public void testSaveCustomer() {
		// fail("Not yet implemented");
	}

}
