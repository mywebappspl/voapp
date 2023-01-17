package net.example.virtualoffice.virtualoffice.KeycloakRestClient.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.KeycloakRestClient;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.ExceptionMessages;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.KeycloakExceptionHandler;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class KeycloakRestClientImpl extends KeycloakRestCaller implements KeycloakRestClient {
    KeycloakReadUserFromRestDTO keycloakReadUserFromRestDTO;
    KeycloakSaveUserToRestDTO keycloakSaveUserToRestDTO;
    KeycloakNewPasswordDTO keycloakNewPasswordDTO;
    AdminStateUpdateToRest adminStateUpdateToRest;
    @Value("${keycloakrestclient.admingroup}")
    protected String adming;
    private static final Logger logger = LoggerFactory.getLogger(KeycloakRestClientImpl.class);


    @Override
    public List<KeycloakReadUserFromRestDTO> getAgentsService() {

        ResponseEntity<String> res = fetchData("/users");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(res.getBody(), mapper.getTypeFactory().constructCollectionType(List.class, KeycloakReadUserFromRestDTO.class));
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException in getAgentsService");
            throw new KeycloakExceptionHandler(ExceptionMessages.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException in getAgentsService");
            throw new KeycloakExceptionHandler(ExceptionMessages.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public KeycloakReadUserFromRestDTO getSingleAgentService(final String id) {
        ResponseEntity<String> res = fetchData("/users/" + id);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(res.getBody(), KeycloakReadUserFromRestDTO.class);
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException in getSingleAgentsService");
            throw new KeycloakExceptionHandler(ExceptionMessages.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException in getSingleAgentsService");
            throw new KeycloakExceptionHandler(ExceptionMessages.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> addAgentService(final KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO) {
        keycloakRestInputUserFromControllerDTO.setEnabled("true");
        KeycloakSaveUserToRestDTO data = keycloakSaveUserToRestDTO.toSave(keycloakRestInputUserFromControllerDTO);
        return saveData("users", data);
    }

    @Override
    public ResponseEntity<String> changePasswordService(final String uid, final KeycloakNewPasswordInput keycloakNewPasswordInput) {
        keycloakNewPasswordDTO.toSave(keycloakNewPasswordInput.getValue());
        return putData("users/" + uid + "/reset-password", keycloakNewPasswordDTO);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<String> updateAgentService(final String uid, final KeycloakRestUpdateAgent keycloakRestUpdateAgent) {
        return putData("users/" + uid, keycloakRestUpdateAgent);
    }

    @Override
    public ResponseEntity<String> agentStatusService(final String uid, final KeycloakRestSetStatus keycloakRestSetStatus) {
        return putData("users/" + uid, keycloakRestSetStatus);
    }

    @Override
    public ResponseEntity<String> agentGroupUpdateService(final String uid, final AdminStateSetFromInput adminStateSetFromInput) {

        if (!adminStateSetFromInput.isState()) {
            return deleteData("users/" + uid + "/groups/" + adming);
        } else {
            return putData("users/" + uid + "/groups/" + adming, null);
        }
    }

    @Override
    public AdminStateSetFromInput agentGroupFetchService(final String uid) {
        ResponseEntity<String> res = fetchData("/users/" + uid + "/groups");
        String noArray = res.getBody().substring(1, res.getBody().length() - 1);
        AdminStateSetFromInput groupState = new AdminStateSetFromInput();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            UserGroupDto ug = mapper.readValue(noArray, UserGroupDto.class);
            if (ug.getName().equals("adming")) {
                groupState.setState(true);
                return groupState;
            } else {
                groupState.setState(false);
                return groupState;
            }
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException in agentGroupFetchService");
            throw new KeycloakExceptionHandler(ExceptionMessages.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException in agentGroupFetchService");
            throw new KeycloakExceptionHandler(ExceptionMessages.INTERNAL_SERVER_ERROR);
        }
    }
}
