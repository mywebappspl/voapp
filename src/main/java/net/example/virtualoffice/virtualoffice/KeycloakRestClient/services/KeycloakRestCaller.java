package net.example.virtualoffice.virtualoffice.KeycloakRestClient.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.Exceptions;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.KeycloakFetchException;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.ServerExceptions;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.KeycloakSaveUserToRestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class KeycloakRestCaller {
    @Value("${keycloakrestclient.baseurl}")
    protected String baseUrl;
    @Value("${keycloakrestclient.realm}")
    protected String realm;
    @Value("${keycloakrestclient.clientid}")
    protected String clientId;
    @Value("${keycloakrestclient.clientsecret}")
    protected String clientSecret;
    @Value("${keycloakrestclient.granttype}")
    protected String grantType;
    @Value("${keycloakrestclient.tokenurl}")
    protected String tokenUrl;
    RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders HeaderBuilder(String accessToken)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        return headers;
    }
    private String UrlSetup(String cmd){
        return baseUrl+realm+'/'+cmd;
    }
    private HttpEntity<?> BuildEntity(Object data, String accessToken){
        return new HttpEntity<>(data,HeaderBuilder(accessToken));
    }
    protected ResponseEntity<String> FetchData(String cmd)
    {
        String url=baseUrl+realm+'/'+cmd;
        TokenPOJO accessToken = ObtainToken();
        if(accessToken.getStatusCode()==200) {
            try{
                return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(HeaderBuilder(accessToken.getAccess_token())), String.class);
            }
            catch (HttpClientErrorException e)
            {
                throw new KeycloakFetchException(e.getRawStatusCode(),e.getResponseBodyAsString());
            }
        }else {
            throw new ServerExceptions(Exceptions.STATUS_TOKEN_ERROR);
        }
    }
    protected ResponseEntity<String> SaveData(String cmd, KeycloakSaveUserToRestDTO data){
        String url=baseUrl+realm+'/'+cmd;
        TokenPOJO accessToken = ObtainToken();
        if(accessToken.getStatusCode()==200) {
            HttpEntity<KeycloakSaveUserToRestDTO> entity = new HttpEntity<>(data,HeaderBuilder(accessToken.getAccess_token()));
            try {
                return restTemplate.postForEntity(url,entity, String.class);
            }
            catch (HttpClientErrorException e)
            {
                throw new KeycloakFetchException(e.getRawStatusCode(),e.getResponseBodyAsString());
            }
        }else {
            throw new ServerExceptions(Exceptions.STATUS_TOKEN_ERROR);
        }
    }
    protected ResponseEntity<String> PutData(String cmd, Object data) {
        TokenPOJO accessToken = ObtainToken();
        if(accessToken.getStatusCode()==200) {
            //HttpEntity<?> entity = new HttpEntity<>(data,HeaderBuilder(accessToken.getAccess_token()));
            try {
                restTemplate.put(UrlSetup(cmd),BuildEntity(data,accessToken.getAccess_token()));
                return ResponseEntity.accepted().build();
            }
            catch (HttpClientErrorException e)
            {
                throw new KeycloakFetchException(e.getRawStatusCode(),e.getResponseBodyAsString());
            }
        }else {
            throw new ServerExceptions(Exceptions.STATUS_TOKEN_ERROR);
        }
    }
    protected ResponseEntity<String> DeleteData(String cmd) {
        TokenPOJO accessToken = ObtainToken();
        if(accessToken.getStatusCode()==200) {
            try {
                return restTemplate.exchange(UrlSetup(cmd),HttpMethod.DELETE,new HttpEntity(HeaderBuilder(accessToken.getAccess_token())),String.class);
            }
            catch (HttpClientErrorException e)
            {
                throw new KeycloakFetchException(e.getRawStatusCode(),e.getResponseBodyAsString());
            }
        }else {
            throw new ServerExceptions(Exceptions.STATUS_TOKEN_ERROR);
        }
    }
    private TokenPOJO ObtainToken()
    {
        try {
        String url=tokenUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class);
        ObjectMapper mapper = new ObjectMapper();

        String json=response.getBody();
            TokenPOJO token = mapper.readValue(json, TokenPOJO.class);
            token.setStatusCode(200);
            return token;
        }
        catch (JsonMappingException e) {
            return new TokenPOJO(500);
        }
        catch (Exception e)
        {
            return new TokenPOJO(500);
        }
    }
}









