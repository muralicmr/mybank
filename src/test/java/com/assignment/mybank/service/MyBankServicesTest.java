package com.assignment.mybank.service;

import com.assignment.mybank.business.domain.MyBankProblemDetails;
import com.assignment.mybank.business.service.AccountService;
import com.assignment.mybank.business.service.MakePaymentService;
import com.assignment.mybank.exception.MyBankException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(MyBankServices.class)
public class MyBankServicesTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private MakePaymentService makePaymentService;

    @Autowired
    private MockMvc mockMvc;

    private final String ACC_INPUT = "{\"firstName\":\"Test\",\"lastName\":\"User\",\"balance\":\"1343.21\"}";

    private final String MAKE_PAYMENT_INPUT = "{\"fromAccount\":2,\"toAccount\":1,\"amount\":\"14623\",\"description\":\"Test transfer\"}";

    @Test
    public void test_createAccount_success() throws Exception {
        this.mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACC_INPUT))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test_createAccount_error() throws Exception {
        given(accountService.createAccount(anyString())).willThrow(new MyBankException(MyBankProblemDetails.SERVICE_UNAVAILABLE));
        this.mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACC_INPUT))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void test_createAccount_error_runtimeException() throws Exception {
        given(accountService.createAccount(anyString())).willThrow(new RuntimeException());
        this.mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACC_INPUT))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void test_makePayment_success() throws Exception {
        this.mockMvc.perform(post("/makePayment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAKE_PAYMENT_INPUT))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test_makePayment_error() throws Exception {
        doThrow(new MyBankException(MyBankProblemDetails.SERVICE_UNAVAILABLE)).when(makePaymentService).makePayment(anyString());
        this.mockMvc.perform(post("/makePayment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAKE_PAYMENT_INPUT))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void test_makePayment_error_runtimeException() throws Exception {
        doThrow(new RuntimeException()).when(makePaymentService).makePayment(anyString());
        this.mockMvc.perform(post("/makePayment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAKE_PAYMENT_INPUT))
                .andExpect(status().is5xxServerError());
    }

}
