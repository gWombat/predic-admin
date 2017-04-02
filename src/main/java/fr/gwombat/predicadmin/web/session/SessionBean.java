package fr.gwombat.predicadmin.web.session;

import fr.gwombat.predicadmin.model.Congregation;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Created by gWombat.
 *
 * @since 02/04/2017
 */
@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {

    private Congregation congregation;

    /**
     *
     * @return the congregation bean associated to the current logged user.
     */
    public Congregation getCongregation() {
        return congregation;
    }

    public void setCongregation(Congregation congregation) {
        this.congregation = congregation;
    }
}
