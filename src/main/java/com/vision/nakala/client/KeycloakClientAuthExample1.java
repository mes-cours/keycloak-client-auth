package com.vision.nakala.client;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

public class KeycloakClientAuthExample1 {

	public static void main(String[] args) {

		KeycloakClientFacade facade = new KeycloakClientFacade("http://localhost:8380/auth", "MULE",
				"composant-agent-service", "eef1ee62-ac72-4be1-bfa5-0be3772f071d");

		String token = facade.getAccessToken("khalifa", "passer");
		System.out.println(token);
	}

	static class KeycloakClientFacade {

		private final String serverUrl;

		private final String realmId;

		private final String clientId;

		private final String clientSecret;

		public KeycloakClientFacade(String serverUrl, String realmId, String clientId, String clientSecret) {
			this.serverUrl = serverUrl;
			this.realmId = realmId;
			this.clientId = clientId;
			this.clientSecret = clientSecret;
		}
		
		public String getAccessToken(String username, String password) {
			return getAccessTokenString(newKeycloakBuilderWithPasswordCredentials(username, password).build());
		}
		
		private String getAccessTokenString(Keycloak keycloak) {
			AccessTokenResponse tokenResponse = getAccessTokenResponse(keycloak);
			return tokenResponse == null ? null : tokenResponse.getToken();
		}
		
		private AccessTokenResponse getAccessTokenResponse(Keycloak keycloak) {
			try {
				return keycloak.tokenManager().getAccessToken();
			} catch (Exception ex) {
				return null;
			}
		}
		
		private KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
			return KeycloakBuilder.builder() //
					.realm(realmId) //
					.serverUrl(serverUrl)//
					.clientId(clientId) //
					.clientSecret(clientSecret) //
					.username(username) //
					.password(password) //
					.grantType(OAuth2Constants.PASSWORD);
		}
		
	}
}
