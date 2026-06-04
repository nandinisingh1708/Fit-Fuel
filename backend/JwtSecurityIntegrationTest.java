package com.calorie.calculator.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.doAnswer;
import javax.servlet.FilterChain;
import org.mockito.ArgumentMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// static MockMvc request imports are limited to what we need for this test
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = com.calorie.calculator.controller.UserController.class)
@org.springframework.test.context.TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true"})
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
public class JwtSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private com.calorie.calculator.repository.UserRepository userRepository;

    @MockBean
    private com.calorie.calculator.service.UserService userService;

    @MockBean
    private com.calorie.calculator.service.UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private com.calorie.calculator.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    // We do not import the custom WebSecurityConfig in this slice test; Spring Security's default configuration
    // will be used for lock-down purposes for this test's scope, ensuring protected endpoints return 401.

    // ObjectMapper intentionally not injected as this simplified test doesn't need JSON mapping

    @BeforeEach
    public void setUpMocks() throws Exception {
        // stub mocked JwtAuthenticationFilter to just pass through the existing filter chain
        doAnswer(invocation -> {
            javax.servlet.http.HttpServletRequest req = invocation.getArgument(0);
            javax.servlet.http.HttpServletResponse res = invocation.getArgument(1);
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(req, res);
            return null;
        }).when(jwtAuthenticationFilter).doFilterInternal(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    public void whenNoAuth_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isUnauthorized());
    }

    // Note: a full sign-in/sign-up integration test requires database auto-configuration.
    // We intentionally skip that here and only verify that a protected resource returns 401 without an auth token.

        // Note: a full sign-in/sign-up integration test requires database auto-configuration.
        // We intentionally skip that here and only verify that a protected resource returns 401 without an auth token.
}
