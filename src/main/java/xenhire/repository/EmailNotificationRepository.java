package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.EmailNotifications;

public interface EmailNotificationRepository extends JpaRepository<EmailNotifications, Long>{

}