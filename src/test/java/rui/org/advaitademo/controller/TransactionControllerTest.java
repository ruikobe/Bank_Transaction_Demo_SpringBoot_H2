package rui.org.advaitademo.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import rui.org.advaitademo.AdvaitaDemoApplication;
import rui.org.advaitademo.model.Transaction;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Transaction Test
 *
 * @author Rui Zhu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdvaitaDemoApplication.class)
@AutoConfigureMockMvc
@Transactional
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    // ---------- save transactions ----------

    @Test
    public void should_create_valid_transaction_and_return_created_status() throws Exception {
        Transaction transaction = new Transaction("2021-08-15 13:26:26",1.02);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(transaction)))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"time\":\"2021-08-15 13:26:26\",\"amount\":1.02}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void should_create_old_transaction_and_return_no_content_status() throws Exception {
        Transaction transaction = new Transaction("2020-08-15 18:10:26",2.02);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(transaction)))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void should_create_notvalid_transaction_and_return_bad_request_status() throws Exception {
        Transaction transaction = new Transaction("2022-08-15 18:10:26",3.02);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(transaction)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    // ---------- get transactions stats ----------


    @Test
    public void should_get_stats() throws Exception {
        mockMvc.perform(get("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    // ---------- delete all transactions ----------

    @Test
    public void deleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/transactions"))
                .andDo(MockMvcResultHandlers.print());
    }
}
