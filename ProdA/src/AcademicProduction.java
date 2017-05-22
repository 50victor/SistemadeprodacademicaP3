

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class AcademicProduction {
    String name;
    int year;

    void createProduction(ArrayList<Project> projects, ArrayList<Collaborator> collaborators) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> indexList;
        int opt = 1;
        while(opt != 0){
            System.out.println("Cast:\n1. Publication\n2. Orientation\n0. Return");
            opt = scan.nextInt();
            switch(opt){
                case 1:
                    indexList = pickCollaborators(collaborators);
                    Publication.createPublication(projects, indexList, collaborators);
                    break;
                case 2:
                    Orientation.createOrientation(collaborators);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, try again.");
                    break;
            }
        }
    }

    private ArrayList<Integer> pickCollaborators(ArrayList<Collaborator> collaborators) throws InputMismatchException{
        ArrayList<Integer> list = new ArrayList<>();
        Collaborator temp = new Collaborator();
        Scanner scan = new Scanner(System.in);
        temp.showCollaboratorsList(collaborators);
        int input = 0;
        while(input != -1){
            System.out.println("Choose collaborator. Insert -1 to return.");
            input = scan.nextInt();
            if(input != -1){
                list.add(input);
            }
        }

        return list;
    }
}
