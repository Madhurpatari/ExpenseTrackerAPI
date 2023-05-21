package com.Geekster.ExpenseTrackerAPI.Repository;

import com.Geekster.ExpenseTrackerAPI.Model.AuthenticationToken;
import com.Geekster.ExpenseTrackerAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);

    AuthenticationToken findFirstByToken(String token);
}
