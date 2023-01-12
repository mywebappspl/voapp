package net.example.virtualoffice.virtualoffice.services.MessagesForLogs;

public enum LogMessages {
    USER_IN_COMPANY_MODIFIED("user {user_name} : {user_id} in company {company_name} : {company_id} modified by agent {agent_name} : {agent_id}"),
    USER_IN_COMPANY_DELETED("user {user_name} : {user_id} in company {company_name} : {company_id} deleted by agent {agent_name} : {agent_id}"),
    USER_IN_COMPANY_CREATED("user {user_name} in company {company_name}:{company_id} created by agent {agent_username}:{agent_id}"),

    COMPANY_MODIFIED("company {company_name} : {company_id} modified by agent {agent_id} : {agent_id}"),
    COMPANY_REMOVED("company {company_name} : {company_id} removed by agent {agent_name} : {agent_id}"),
    COMPANY_CREATED("company {company_name} created by agent {agent_name} : {agent_id}"),

    AGENT_CREATED("agent {targeted_agent_name} created by agent {agent_name} : {agent_id}"),
    AGENT_MODIFIED("agent {targeted_agent_name} : {targeted_agent_id} modified by agent {agent_name} : {agent_id}"),
    PASSWORD_RESETED("password reseted for agent {targeted_agent_name} : {targeted_agent_id}  by agent {agent_name} : {agent_id}"),
    AGENT_REMOVED("agents {targeted_agent_name} : {targeted_agent_id}  removed by agent {agent_name} : {agent_id}"),
    AGENT_PROMOTED("agent {targeted_agent_name} : {targeted_agent_id} promoted to admin by agent {agent_name} : {agent_id}"),
    AGENT_DOWNGRADED("agent {targeted_agent_name} : {targeted_agent_id} downgraded to user by agent {agent_name} : {agent_id}");
    String message;
    LogMessages(String messsage){
        this.message=messsage;
    }

    public String getMessage() {
        return message;
    }
}
