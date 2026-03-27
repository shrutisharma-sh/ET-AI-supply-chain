package com.shruti.supply_chain.agent;

import com.shruti.supply_chain.agent.AgentAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentAuditRepository extends JpaRepository<AgentAuditLog, Long> {
}
