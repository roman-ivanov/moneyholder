package ua.pp.bizon.moneyholder.application.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.bizon.moneyholder.application.datasource.Error;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationManager {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserDetailsManager userDetailsManager;

    @RequestMapping("/login")
    public ResponseEntity login(@RequestParam(value="username")String user, @RequestParam(value="password") String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(user);
        if (userDetails != null && !userDetails.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("wrong password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return ResponseEntity.ok().body("logged in " + user);
    }


    @RequestMapping("/authentication/session")
    public ResponseEntity session(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("auth status: " + auth);
        return auth == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error("no active session")) :
                ResponseEntity.ok(auth.getPrincipal());
    }
}
