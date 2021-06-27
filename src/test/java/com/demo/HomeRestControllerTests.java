package com.demo;

import com.rest.api.controller.HomeRestController;
import org.junit.Test;

/**
 *
 * @author Muhammad Atta
 *
 */

public class HomeRestControllerTests {

    @Test
    public void testHomeController() {
        HomeRestController homeController = new HomeRestController();
        String result = homeController.sayHello();
        assertEquals(result, "Hello World!");
    }

    private void assertEquals(String result, String s) {
    }
}