package com.Geekster.ExpenseTrackerAPI.Repository;

import com.Geekster.ExpenseTrackerAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
