package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.Utils.voutils;
import net.example.virtualoffice.virtualoffice.exception.LogsExceptionHandler;
import net.example.virtualoffice.virtualoffice.model.Log;
import net.example.virtualoffice.virtualoffice.repository.AgentsRepository;
import net.example.virtualoffice.virtualoffice.repository.CompaniesRepository;
import net.example.virtualoffice.virtualoffice.repository.LogsRepository;
import net.example.virtualoffice.virtualoffice.repository.MembersRepository;
import net.example.virtualoffice.virtualoffice.services.MessagesForLogs.LogMessages;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogService {

    private static final Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);
    private String message;
    private int company_id;
    private int agent_id;

    private final Map<String, String> map = new HashMap<>();
    private final LogsRepository logsRepository;
    private final CompaniesRepository companiesRepository;
    private final MembersRepository membersRepository;
    private final AgentsRepository agentsRepository;

    private static String IntToStr(int number) {
        return Integer.toString(number);
    }

    public void addAgentId(int agentId) {
        this.map.put("agent_username", GetAgentUsername(agentId));
        this.map.put("agent_id", IntToStr(agentId));
    }

    public void addAgentName(String agentName) {
        this.map.put("agent_name", agentName);
    }

    public void addCompanyId(int companyId) {
        this.map.put("company_name", GetComapnyName(companyId));
        this.map.put("company_id", IntToStr(companyId));
    }

    public void addCompanyName(String companyName) {
        this.map.put("company_name", companyName);
    }

    public void addMemberId(int memberId) {
        this.map.put("user_name", GetMemberName(memberId));
        this.map.put("user_id", IntToStr(memberId));
    }

    public void addMemberName(String memberName) {
        this.map.put("user_name", memberName);
    }

    public void addTargetedAgentId(int tAgentId) {
        this.agentsRepository.findAgentUsernameById(tAgentId);
        this.map.put("targeted_agent_id", IntToStr(tAgentId));
    }

    public void addTargetedAgentName(String tAgentName) {
        this.map.put("targeted_agent_id", tAgentName);
    }

    public LogService(final LogsRepository logsRepository, final CompaniesRepository companiesRepository, final MembersRepository membersRepository, final AgentsRepository agentsRepository) {
        this.logsRepository = logsRepository;
        this.agentsRepository = agentsRepository;
        this.membersRepository = membersRepository;
        this.companiesRepository = companiesRepository;

    }

    private String GetAgentUsername(int id) {
        return agentsRepository.findAgentUsernameById(id);
    }

    private String GetComapnyName(int id) {
        return companiesRepository.findCompanyNameById(id);
    }

    private String GetMemberName(int id) {
        return membersRepository.findMemberNameById(id);
    }

    public void SaveLog(final LogMessages messages) {
        try {
            this.message = messages.getMessage();
            this.GetMessages();
            logsRepository.save(ToLogEntity());
        } catch (Exception e) {
            logger.error("Error when creating log");
        }
    }

    private Log ToLogEntity() {
        Log log = new Log();
        log.setCreatedOn(LocalDateTime.now());
        log.setAgentId(this.agent_id);
        log.setCompanyId(this.company_id);
        log.setMessage(this.message);
        return log;


    }

    private String GetMessages() {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("company_id"))
                this.company_id = Integer.parseInt(entry.getValue());
            if (entry.getKey().equals("agent_id"))
                this.agent_id = Integer.parseInt(entry.getValue());
            if (this.message.contains(entry.getKey())) {
                this.message = this.message.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return this.message;
    }

    public Page<Log> GetLogs(Pageable pageable) {
        return logsRepository.findAll(pageable);
    }

    public Page<Log> GetLogsByDate(final Pageable pageable, Date startDate, Date endDate) {
        if (voutils.LogsDatesValidation(startDate, endDate))
            return logsRepository.findAllByCreatedOn(startDate, Date.from(voutils.EndDateCalculation(endDate)), pageable);
        else
            throw new LogsExceptionHandler(" Wrong dates");
    }
}
