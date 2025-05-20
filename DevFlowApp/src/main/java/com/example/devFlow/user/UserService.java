package com.example.devFlow.user;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.devFlow.exception.UserAlreadyExistsException;
import com.example.devFlow.exception.UsernameTaken;
import com.example.devFlow.registration.RegistrationRequest;
import com.example.devFlow.registration.RegistrationRequestEmail;
import com.example.devFlow.registration.token.*;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,VerificationTokenRepository tokenRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenRepository=tokenRepository;
    	
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    

    public User registerUserDetails(RegistrationRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UsernameTaken("Username already in use");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setEnabled(true);
        User saved = userRepository.save(user);
        System.out.println("SAVED USER ID = " + saved.getId());
        return saved;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
    
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    
    public void saveUserVerificationToken(User theUser, String token) {
		var verificationToken=new VerificationToken(token,theUser);
		tokenRepository.save(verificationToken);
	}

	public String validateToken(String theToken) { //validate by expiration date
		VerificationToken token=tokenRepository.findByToken(theToken);
		if(token==null) { //token has been deleted
			return "Invalid verification token";
		}
		User user=token.getUser();
		Calendar calendar=Calendar.getInstance();
		if(token.getExpirationTime().getTime()-calendar.getTime().getTime() <=0) {
			tokenRepository.delete(token); //delete expired token
			return "Token already exired";
		}
		//if token is not expired, enable user
		user.setEnabled(true);
		userRepository.save(user);
		return "Valid";
	}
    


}
