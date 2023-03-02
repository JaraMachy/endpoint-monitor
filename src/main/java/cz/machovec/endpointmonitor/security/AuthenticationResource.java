package cz.machovec.endpointmonitor.security;

import cz.machovec.endpointmonitor.exceptions.InvalidUsernameOrPasswordException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Gets JWT token for given credentials.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "401", description = "unauthorized")})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationReqTo authenticationReqTo) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReqTo.getUsername(), authenticationReqTo.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidUsernameOrPasswordException();
        }
        // At this point authentication is sucessfull

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationReqTo.getUsername());

        final String jwt = jwtUtils.generateAccessToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResTo(jwt));
    }

    //~ Transfer objects

    @Getter @Setter
    public static class AuthenticationReqTo {
        @NotBlank
        @NotNull
        private String username;
        @NotBlank
        @NotNull
        private String password;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResTo {
        private String jwtToken;
    }

}
