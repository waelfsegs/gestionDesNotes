package com.pfe.gestionnote.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String Etudiant = "ROLE_ETUDIANT";
    public static final String Agent = "ROLE_AGENT";
    public static final String Enseignant = "ROLE_Enseignant";
    public static final String chefDepartement = "ROLE_ChefDepartement";

    private AuthoritiesConstants() {
    }
}
