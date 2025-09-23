package spring.gemfire.showcase.account.server.account.listeners.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public record AccountEntity(@Id  String id, String name) {
}
