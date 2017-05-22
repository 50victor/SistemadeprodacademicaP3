

import java.util.ArrayList;

class Professor extends Collaborator {
    ArrayList<Orientation> orientations = new ArrayList<>();

    Professor(){

    }

    Professor(String name, String email){
        this.name = name;
        this.email = email;
    }

}
