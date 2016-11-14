package com.infinite.eoa.router.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath:*spring/dispatcher-servlet.xml",
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-schedule.xml",
        "classpath:/spring/application-service.xml",
        "classpath:/spring/application-jms.xml",
        "classpath:/spring/application-thread-pool.xml",
        "classpath:/spring/application-dao.xml"
})
public class CencusControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCencusComponentWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/temp/department"))
                .andDo(MockMvcResultHandlers.print());
//        mockMvc.perform(MockMvcRequestBuilders.get("/cencus/component/work?id=988b743c-3a73-4485-931e-379653f0593f&start=1478314153655&end=1478486953792"))
//                .andDo(MockMvcResultHandlers.print());
    }

}