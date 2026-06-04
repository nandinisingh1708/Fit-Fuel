package com.calorie.calculator.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.calorie.calculator.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.FilterChainProxy;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"})
@Import(WebSecurityConfig.class)
public class WebSecurityConfigTest {

    @Autowired
    private FilterChainProxy filterChainProxy;

    // Mock some dependencies that the security config would normally wire
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private com.calorie.calculator.repository.UserRepository userRepository;
    @MockBean
    private com.calorie.calculator.service.UserService userService;
    @MockBean
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void filterChain_shouldContainJwtAuthenticationFilter() {
        assertThat(filterChainProxy.getFilterChains()).isNotEmpty();
        boolean found = filterChainProxy.getFilterChains().stream()
                .flatMap(chain -> chain.getFilters().stream())
                .anyMatch(f -> f instanceof JwtAuthenticationFilter);
        assertThat(found).isTrue();
    }
}
