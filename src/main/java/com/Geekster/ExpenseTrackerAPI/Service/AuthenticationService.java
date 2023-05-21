package com.Geekster.ExpenseTrackerAPI.Service;

import com.Geekster.ExpenseTrackerAPI.Model.AuthenticationToken;
import com.Geekster.ExpenseTrackerAPI.Model.User;
import com.Geekster.ExpenseTrackerAPI.Repository.IAuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private IAuthenticationTokenRepository tokenRepository;

    public void saveToken(AuthenticationToken token) {
        tokenRepository.save(token);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public boolean authenticate(String email, String token) {
        if(token==null && email==null){
            return false;
        }
        AuthenticationToken authToken = tokenRepository.findFirstByToken(token);
        if(authToken==null){
            return false;
        }
        String expectedEmail = authToken.getUser().getEmail();
        return expectedEmail.equals(email);

    }

    public void deleteToken(String token) {
        AuthenticationToken existingToken = tokenRepository.findFirstByToken(token);
        tokenRepository.deleteById(existingToken.getTokenId());
    }
}
