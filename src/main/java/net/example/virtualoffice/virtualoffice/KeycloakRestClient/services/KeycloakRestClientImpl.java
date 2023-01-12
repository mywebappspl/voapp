package net.example.virtualoffice.virtualoffice.KeycloakRestClient.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.KeycloakRestClient;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.BadRequestException;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.Exceptions;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception.ServerExceptions;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.strefaphp.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;

import java.util.List;


@Component

public class KeycloakRestClientImpl extends KeycloakRestCaller implements KeycloakRestClient {
    KeycloakReadUserFromRestDTO keycloakReadUserFromRestDTO;
    KeycloakSaveUserToRestDTO keycloakSaveUserToRestDTO;
    KeycloakNewPasswordDTO keycloakNewPasswordDTO;
    AdminStateUpdateToRest adminStateUpdateToRest;
    @Value("${keycloakrestclient.admingroup}")
    protected String adming;
    @Value("${keycloakrestclient.usergroup}")
    protected String userg;

    KeycloakRestClientImpl(final KeycloakReadUserFromRestDTO keycloakReadUserFromRestDTO, final KeycloakSaveUserToRestDTO keycloakSaveUserToRestDTO, final KeycloakNewPasswordDTO keycloakNewPasswordDTO, final AdminStateUpdateToRest adminStateUpdateToRest) {
        this.keycloakReadUserFromRestDTO = keycloakReadUserFromRestDTO;
        this.keycloakSaveUserToRestDTO = keycloakSaveUserToRestDTO;
        this.keycloakNewPasswordDTO = keycloakNewPasswordDTO;
        this.adminStateUpdateToRest = adminStateUpdateToRest;
    }

    @Override
    public List<KeycloakReadUserFromRestDTO> GetAgents() {
        ResponseEntity<String> res = FetchData("/users");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(res.getBody(), mapper.getTypeFactory().constructCollectionType(List.class, KeycloakReadUserFromRestDTO.class));
        } catch (JsonMappingException e) {
            throw new ServerExceptions(Exceptions.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw new ServerExceptions(Exceptions.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public KeycloakReadUserFromRestDTO GetSingleAgent(final String id) {
        ResponseEntity<String> res = FetchData("/users/" + id);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(res.getBody(), KeycloakReadUserFromRestDTO.class);
        } catch (JsonMappingException e) {
            throw new ServerExceptions(Exceptions.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw new ServerExceptions(Exceptions.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> AddAgent(final KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO) {
        keycloakRestInputUserFromControllerDTO.setEnabled("true");
        KeycloakSaveUserToRestDTO data = keycloakSaveUserToRestDTO.ToSave(keycloakRestInputUserFromControllerDTO);
        return SaveData("users", data);
    }

    @Override
    public ResponseEntity<String> ChangePassword(final String uid, final KeycloakNewPasswordInput keycloakNewPasswordInput) {
        keycloakNewPasswordDTO.ToSave(keycloakNewPasswordInput.getValue());
        return PutData("users/" + uid + "/reset-password", keycloakNewPasswordDTO);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<String> UpdateAgent(final String uid, final KeycloakRestUpdateAgent keycloakRestUpdateAgent) {
        if (keycloakRestUpdateAgent.getEmail().equals(null))
            throw new BadRequestException(Exceptions.AGENT_EMAIL_NOT_EMPTY_BAD_REQUEST);
        if (keycloakRestUpdateAgent.getLastName().equals(null))
            throw new BadRequestException(Exceptions.AGENT_LASTNME_NOT_EMPTY_REQUEST);
        if (keycloakRestUpdateAgent.getFirstName().equals(null))
            throw new BadRequestException(Exceptions.AGENT_FIRSTNAME_NOT_EMPTY_BAD_REQUEST);
        return PutData("users/" + uid, keycloakRestUpdateAgent);
    }

    @Override
    public ResponseEntity<String> AgentStatus(final String uid, final KeycloakRestSetStatus keycloakRestSetStatus) {
        return PutData("users/" + uid, keycloakRestSetStatus);
    }

    @Override
    public ResponseEntity<String> AgentGroupUpdate(final String uid, final AdminStateSetFromInput adminStateSetFromInput) {

        if (!adminStateSetFromInput.isState()) {
            return DeleteData("users/" + uid + "/groups/" + adming);
        } else {
            return PutData("users/" + uid + "/groups/" + adming, null);
        }
    }
    @Override
    public AdminStateSetFromInput AgentGroupFetch(final String uid)
    {
        ResponseEntity<String> res = FetchData("/users/" + uid + "/groups");
        String noArray = res.getBody().substring(1,res.getBody().length()-1);
        AdminStateSetFromInput groupState = new AdminStateSetFromInput();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            UserGroupDto ug = mapper.readValue(noArray, UserGroupDto.class);
            if(ug.getName().equals("adming")) {
                groupState.setState(true);
                return groupState;
            }
            else {
                groupState.setState(false);
                return groupState;
            }
        } catch (JsonMappingException e) {
           throw new ServerExceptions(Exceptions.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw new ServerExceptions(Exceptions.INTERNAL_SERVER_ERROR);
        }
    }
}
