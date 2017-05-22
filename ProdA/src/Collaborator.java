
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Collaborator {
    String name;
    String email;
    ArrayList<Publication> publications = new ArrayList<>(3);
    ArrayList<Project> projects = new ArrayList<>();

    Collaborator(){}

    private Collaborator(String name, String email){
        this.name = name;
        this.email = email;
    }

    /* RECEBE o array de colaboradores em que será buscado
     * e a String com o nome a ser buscado.
     * RETORNA a posição em que a String foi primeiro encontrada
     * se nenhuma ocorrência for encontrada, retorna -1
     */
    static int searchCollaborator(ArrayList<Collaborator> collaborators, String searched){
        int position = -1;
        if(collaborators == null) return -1;
        for(int i = 0; i < collaborators.size(); i++){
            if(collaborators.get(i).name.equals(searched)){
                position = i;
            }
        }
        return position;
    }

    void printCollaborators(ArrayList<Collaborator> collaborators){
        for (Collaborator collab : collaborators){
            if(collab instanceof Professor){
                System.out.print(collab.name + " (Professor) | ");
            } else {
                System.out.print(collab.name + " | ");
            }
        }
        System.out.println();
    }

    void showCollaboratorsList(ArrayList<Collaborator> collaborators){
        for(int i = 0; i < collaborators.size(); i++){
            System.out.println(i + ". " + collaborators.get(i).name);
        }
    }

    void collaboratorConsultation(ArrayList<Collaborator> collaborators) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        boolean valid = false;
        System.out.println("Choose collaborator.");
        showCollaboratorsList(collaborators);
        while(!valid){
            int opt = scan.nextInt();
            if(opt < -1 || opt > collaborators.size()){
                System.out.println("Invalid option.");
                valid = false;
            } else {
                if(opt == -1){
                    return;
                } else {
                    displayCollaboratorInfo(collaborators.get(opt));
                }
                valid = true;
            }
        }
    }

    private void displayCollaboratorInfo(Collaborator collaborator){
        System.out.println("Name: "+ collaborator.name);
        System.out.println("Email: "+ collaborator.email);
        if(collaborator instanceof Professor){
            System.out.println("Professor.");
            if(((Professor) collaborator).orientations.size() > 0){
                System.out.print("Orientations: ");
                for(Orientation orien : ((Professor) collaborator).orientations){
                    System.out.print(orien.name +" | ");
                }
                System.out.println();
            }
        }
        if(collaborator.publications.size() > 0){
            System.out.print("Publications: ");
            for(Publication pub : collaborator.publications){
                System.out.print(pub.name +"(" + pub.year + ") | ");
            }
            System.out.println();
        }

        if(collaborator.projects.size() > 0){
            System.out.println("Projects: ");
            for(Project proj : collaborator.projects){
                if(proj.getStatusPos() == 0){
                    System.out.print(proj.getProjectName() + " (On elaboration) | ");
                }
                else if(proj.getStatusPos() == 1){
                    System.out.print(proj.getProjectName() + " (Ongoing) | ");
                }
                else if(proj.getStatusPos() == 2){
                    System.out.print(proj.getProjectName() + " (Finished) | ");
                }
            }
            System.out.println();
        }

    }

    ArrayList<Collaborator> collabCreator(ArrayList<Collaborator> collaborators) throws InputMismatchException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please, insert the collaborator's name.");
        String name = scan.nextLine();
        int aux = searchCollaborator(collaborators, name);
        if(aux != -1){
            System.out.println("This collaborator already exists.");
            return collaborators;
        }
        System.out.println("Please, insert the collaborator's email.");
        String email = scan.nextLine();
        System.out.println("Is this collaborator a professor? 1. Yes; 2. No");
        int opt;
        do {
           opt = scan.nextInt();
            if(opt == 1){
                Professor professor = new Professor(name, email);
                collaborators.add(0, professor);
            } else if (opt == 2){
                Collaborator collaborator = new Collaborator(name, email);
                collaborators.add(collaborator);
            }
        }while(opt != 1 && opt != 2);

        return collaborators;
    }

}
