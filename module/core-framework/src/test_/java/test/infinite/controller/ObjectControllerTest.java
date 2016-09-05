package test.infinite.controller;

import com.infinite.framework.bson.filter.PersistentObjectFilters;
import com.infinite.framework.bson.filter.PersistentObjectUpdates;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.types.ObjectId;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath:/spring/application-*.xml",
        "classpath:/spring/dispatcher-servlet.xml",
})
public class ObjectControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void putTest() throws Exception {
        this.mockMvc.perform(put("/obj")
                .header("APPKEY", "adsfsdf")
                .param("namespace", "test")
                .param("data",
                        new Document("name", "0-0")
                                .append("subs", Arrays.asList(
                                        new Document("_id", new ObjectId())
                                                .append("subs", new BsonArray())
                                                .append("name", "1-1"),
                                        new Document("_id", new ObjectId())
                                                .append("subs", new BsonArray())
                                                .append("name", "1-2")
                                ))
                                .toJson()
                )
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.obj").isString());
        ;
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/obj")
                .header("APPKEY", "adsfsdf")
                .param("namespace", "test")
                .param("filter", PersistentObjectFilters.eq("_id", "57c5638c2dbcae1b1e238602").toJson())
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.obj").isNotEmpty());
        ;
    }

    @Test
    public void updateTest() throws Exception {
        this.mockMvc.perform(post("/obj/update")
                .header("APPKEY", "adsfsdf")
                .param("namespace", "test")
                .param("filter",
                        PersistentObjectFilters
                                .eq("_id", "57c584d32dbcae4c7ee2d2be")
                                .and(PersistentObjectFilters.elemMatch("subs",
                                        PersistentObjectFilters.eq("_id", "57c584d32dbcae4c7ee2d2bc")
                                ))
                                .toJson())
                .param("updates", PersistentObjectUpdates.combineToJson(
                        PersistentObjectUpdates.addToSet("subs",
                                new Document("_id", new ObjectId())
                                        .append("subs", new BsonArray())
                                        .append("name", "1-1-1"))
//                        PersistentObjectUpdates.set("name", "edit")
                ))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.obj").isNotEmpty());
        ;
    }

}