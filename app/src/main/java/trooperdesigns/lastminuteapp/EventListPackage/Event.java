package trooperdesigns.lastminuteapp.EventListPackage;

import android.graphics.Color;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import trooperdesigns.lastminuteapp.EventDetailPackage.Invitee;

/**
 * Created by james on 15-08-10.
 */

@Data
@AllArgsConstructor
public class Event {
    private String title;
    private String eventDescription;
    private Date eventDate;
    private Status status;
    private List<Invitee> inviteeList;

    @AllArgsConstructor
    public enum Status {
        HAPPENING(Color.parseColor("#99006400")),
        NON_HAPPENING(Color.parseColor("#99ff0000")),
        PENDING(Color.parseColor("#99FF9900"));

        @Getter
        private int color;
    }
}
