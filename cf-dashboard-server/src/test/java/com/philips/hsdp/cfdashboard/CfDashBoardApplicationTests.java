package com.philips.hsdp.cfdashboard;

import com.google.gson.*;
import com.philips.hsdp.cfdashboard.controller.*;
import com.philips.hsdp.cfdashboard.model.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CfDashBoardLoginController.class)
public class CfDashBoardApplicationTests {
    @Autowired
    private MockMvc mvc;
    
    private Gson gson = new Gson();
    
    @Test
    public void doLogin() throws Exception {
        Login login = new Login();
        login.setPassword("testPassword");
        login.setUserName("testUserName");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        mvc.perform(MockMvcRequestBuilders
                            .post("/cfdashboard/login")
                            .content(gson.toJson(login))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                });
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}


