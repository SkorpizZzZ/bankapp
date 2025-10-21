package org.company.exchangegenerator;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Testcontainers
@ImportTestcontainers({KafkaTestContainer.class})
@SpringBootTest
@ActiveProfiles("test")
public class AbstractIntegration {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockitoBean
    private OAuth2AuthorizedClientService authorizedClientService;

    @BeforeEach
    void setUp() {
        // Настройка моков
        when(jwtDecoder.decode(anyString())).thenReturn(
                Jwt.withTokenValue("mock-token")
                        .header("alg", "none")
                        .claim("sub", "test-user")
                        .claim("resource_access", Map.of(
                                "account", Map.of(
                                        "roles", List.of("ROLE_USER", "ROLE_ADMIN")
                                )
                        ))
                        .build()
        );
    }
}
