package cz.machovec.endpointmonitor.security;

public class SecurityMappers {

    public static UserDetailsImpl fromSecUser(SecUser secUser) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(secUser.getId());
        userDetails.setUsername(secUser.getUsername());
        userDetails.setPassword(secUser.getPassword());

        return userDetails;
    }
}
