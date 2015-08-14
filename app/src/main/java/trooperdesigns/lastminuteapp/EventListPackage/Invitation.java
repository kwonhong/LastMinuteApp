package trooperdesigns.lastminuteapp.EventListPackage;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by james on 15-08-10.
 */
@Data
@AllArgsConstructor
public class Invitation {

    private Status status;
//    private String name;

    public enum Status {
        PENDING, ACCEPT, DECLINE, ON_MY_WAY;
    }
}
