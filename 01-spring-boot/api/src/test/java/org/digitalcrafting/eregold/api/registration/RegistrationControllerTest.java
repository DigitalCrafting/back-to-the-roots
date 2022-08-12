package org.digitalcrafting.eregold.api.registration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationControllerTest {

    @Autowired
    private RegistrationController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationControllerService service;

    private RegisterRequest mockRequest;
    private Gson gson = new Gson();

    @BeforeAll
    public void setup() {
        mockRequest = new RegisterRequest();
        mockRequest.setEmail("test");
        mockRequest.setPassword(new char[] { 'a', 'b', 'c'});
        mockRequest.setFirstName("John");
        mockRequest.setLastName("MacMillan");
    }

    @Test
    public void should_createRegistrationController() {
        assertNotNull(controller);
    }

    @Test
    public void should_callRegisterMethod_inControllerService() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(mockRequest))
        )
                .andExpect(status().isOk());

        verify(service, atMostOnce()).register(mockRequest);
    }
}