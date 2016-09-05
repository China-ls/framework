package test.infinite.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath:/spring/application-*.xml",
        "classpath:/spring/dispatcher-servlet.xml",
})
public class VirtualSensorControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/sensor/all")
                .header("APPKEY", "adsfsdf")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void getSensorLatestData() throws Exception {
        this.mockMvc.perform(get("/sensor/{id}/data/latest", "59ec0ac4-2182-4960-9166-3ce62738ef99")
                .header("APPKEY", "adsfsdf")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void getSensorBettwen() throws Exception {
        this.mockMvc.perform(get("/sensor/841188c2-b170-4e91-9234-5df4b255dc1c/data/between")
                .param("start", "1471351883")
                .header("APPKEY", "adsfsdf")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void getSensorByIds() throws Exception {
        this.mockMvc.perform(get("/sensor/")
                .header("APPKEY", "adsfsdf")
                .param("ids", "59ec0ac4-2182-4960-9166-3ce62738ef99")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.obj").isArray());
    }

    @Test
    public void testFieldChange() throws Exception {
        this.mockMvc.perform(get("/sensor/{id}/data/fieldchange", "988b743c-3a73-4485-931e-379653f0593f")
                .header("APPKEY", "adsfsdf")
                .param("field", "positive_total")
                .param("days", "2016-08-25,2016-08-24,2016-08-23")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
//                .andExpect(jsonPath("$.obj").isArray());
    }

    @Test
    public void findDataById() throws Exception {
        this.mockMvc.perform(get("/sensor/data")
                .header("APPKEY", "adsfsdf")
                .param("id", "57c393462dbcae21aaaacdab")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.obj").isNotEmpty());
    }

}