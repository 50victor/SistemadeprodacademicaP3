

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Orientation extends AcademicProduction {
    Professor professor;
    String message;
    static private int numberOfOrientations = 0;

    private Orientation(Professor professor, String message, String name, int year){
        this.professor = professor;
        this.message = message;
        this.name = name;
        this.year = year;
    }

    static int getNumberOfOrientations() {
        return numberOfOrientations;
    }

    static Orientation createOrientation(ArrayList<Collaborator> collaborators) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        Professor tempProf = new Professor();
        System.out.println("Insert the professor's name.");
        String profName = scan.nextLine();
        int aux = tempProf.searchCollaborator(collaborators, profName);
        if(aux == -1){
            System.out.println("Not found.");
            return null;
        } else if(!(collaborators.get(aux) instanceof Professor)){
            System.out.println("Not a professor.");
            return null;
        } else {
            tempProf = ((Professor) collaborators.get(aux));
            System.out.println("Insert the orientation's name.");
            String name = scan.nextLine();
            System.out.println("Inser the orientation's year.");
            int year = scan.nextInt();
            System.out.println("Type the orientation's message to the lab.");
            scan.next();
            String msg = scan.nextLine();
            Orientation orientation = new Orientation(tempProf, msg, name, year);
            tempProf.orientations.add(orientation);
            numberOfOrientations++;
            return orientation;
        }

    }
}
