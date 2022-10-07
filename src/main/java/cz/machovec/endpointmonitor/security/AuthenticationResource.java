package cz.machovec.endpointmonitor.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class AuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationReqTo authenticationReqTo) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReqTo.getUsername(), authenticationReqTo.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        // At this point authentication is successfully

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationReqTo.getUsername());

        final String jwt = jwtUtils.generateAccessToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResTo(jwt));
    }

    //~ Transfer objects

    @Getter @Setter
    private static class AuthenticationReqTo {
        @NotBlank
        @NotNull
        private String username;
        @NotBlank
        @NotNull
        private String password;
    }

    @Getter @Setter
    @AllArgsConstructor
    private static class AuthenticationResTo {
        private String jwtToken;
    }

}
