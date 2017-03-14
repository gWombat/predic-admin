package fr.gwombat.predicadmin.model;

import java.util.UUID;

/**
 * Created by gWombat.
 *
 * @since 14/03/2017
 */
class EntityIdGenerator {

    static String generateIdentifier() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

}
