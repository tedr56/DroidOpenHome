package rocher.adrien.festiopenhome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ted on 22/08/15.
 */

public class ClientRoom {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public ClientRoom(String string) {
        this.string = string;
    }

}