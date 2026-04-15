package com.personalresume.domain.contact;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByIsVisibleTrue(Sort sort);
    List<Contact> findByProfileIdAndIsVisibleTrue(Long profileId, Sort sort);
}
