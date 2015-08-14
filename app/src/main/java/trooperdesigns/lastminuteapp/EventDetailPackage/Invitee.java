package trooperdesigns.lastminuteapp.EventDetailPackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import trooperdesigns.lastminuteapp.EventListPackage.Invitation;

/**
 * Created by james on 15-08-12.
 */

@Data @AllArgsConstructor
public class Invitee {

    private Invitation.Status status;
    private String name;

}
