package ua.pp.bizon.moneyholder.application.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByUserId(long id);
}