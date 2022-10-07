package cz.machovec.endpointmonitor.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final @NonNull SecUserRepository secUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {

        SecUser secUser = secUserRepository.findByUsername(username);

        if (secUser == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }

        return SecurityMappers.fromSecUser(secUser);
    }
}
