package com.dh.gatewayservice;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class CuentaController {
    @GetMapping(path = "/detail/{id}")
    public Map<String, Object> detailSecure
            (@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient, /* Atributo que pertenece
             al API de OAuth de Spring. Incluye todos los datos relacionados al token,
             en base a las validaciones y adiciones del proveedor (como la fecha de expiración y de creación) */
             Authentication auth, /* Objeto sobre el cual Spring Security identifica a un usuarie
             dentro del contexto de Spring con todos sus datos validados */
             @PathVariable("id") Long idAccount) {

        Map<String, Object> response = new HashMap<>();
        response.put("clientName", authorizedClient.getClientRegistration().getClientName());
        response.put("accessToken", authorizedClient.getAccessToken());
        response.put("authName", auth.getDetails());
        response.put("id_account", idAccount);

        return response;

    }
}
