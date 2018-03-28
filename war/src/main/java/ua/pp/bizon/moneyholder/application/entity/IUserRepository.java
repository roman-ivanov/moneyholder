package ua.pp.bizon.moneyholder.application.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; 


@Transactional
@Repository
public interface IUserRepository extends JpaRepository < UserEntity, Long > {

    UserEntity findByName(String name);
}

