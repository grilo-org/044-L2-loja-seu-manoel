    package com.seumanoel.controller;

    import com.seumanoel.config.BaseTestConfig;
    import com.seumanoel.config.TestSecurityConfig;
    import com.seumanoel.security.JwtTokenProvider;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.boot.test.mock.mockito.MockBean;
    import org.springframework.context.annotation.Import;
    import org.springframework.test.context.ActiveProfiles;
    import org.springframework.test.web.servlet.MockMvc;

    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    @WebMvcTest(TestController.class)
    @Import(TestSecurityConfig.class)
    @MockBean(JwtTokenProvider.class)
    @ActiveProfiles("test")
    public class TestControllerTest{

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testHello_Success() throws Exception {
            mockMvc.perform(get("/api/test"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Hello from Seu Manoel's Store API!"));
        }
    }
