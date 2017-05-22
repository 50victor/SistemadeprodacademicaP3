
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Adicionando um professor e um projeto para facilitar
        Professor professorTeste = new Professor("Professorteste", "professorteste@email.com");
        Project projetoTeste = new Project("Projetoteste", "Objetivoteste", professorTeste,
                "Descriçãoteste", "Agenciateste", 10000);
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Collaborator> collaborators = new ArrayList<>();
        boolean valid = false;
        collaborators.add(0, professorTeste);
        professorTeste.projects.add(projetoTeste);
        projects.add(projetoTeste);

       while(!valid){
            try{
                menu(projects, collaborators);
                valid = true;
            } catch (InputMismatchException e){
                System.out.println("Invalid entry! Returning to the main menu...");
            } catch(Exception e){
                System.out.println("Something unexpected happened! Returning to the main menu...");
            }
        }

    }

    private static void menu(ArrayList<Project> projects, ArrayList<Collaborator> collaborators) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        int option = 1;
        while(option != 0){
            System.out.println("Main Menu:\n");
            System.out.println("1. Register collaborator.\n2. Create projects\n3. Edit projects");
            System.out.println("4. Create publications\n5. Queries\n6. Laboratory reports\n0. Leave");
            option = scan.nextInt();
            optionSwitcher(option, projects, collaborators);
        }
    }

    private static void optionSwitcher(int option, ArrayList<Project> projects, ArrayList<Collaborator> collaborators) {
        Project tempProject = new Project();
        Collaborator tempCollaborator = new Collaborator();
        AcademicProduction tempProduction = new AcademicProduction();
        switch(option){
            case 1:
                tempCollaborator.collabCreator(collaborators);
                break;
            case 2:
                tempProject.projectCreator(projects, collaborators);
                break;
            case 3:
                tempProject.editProject(collaborators, projects);
                break;
            case 4:
                tempProduction.createProduction(projects, collaborators);
                break;
            case 5:
                consultations(collaborators, projects);
                break;
            case 6:
                tempProject.labReport(collaborators, projects);
                break;
            case 0:
                //voltar ao menu principal
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private static void consultations(ArrayList<Collaborator> collaborators, ArrayList<Project> projects) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        Project tempProject = new Project();
        Collaborator tempCollab = new Collaborator();
        boolean valid = false;
        int opt = 1;
        while(opt != 0){
            System.out.println("1. Search colaborators\n2. Search projects\n0. Main Menu.");
            opt = scan.nextInt();
            switch(opt){
                case 1:
                    valid = false;
                    while(!valid){
                        try {
                            tempCollab.collaboratorConsultation(collaborators);
                            valid = true;
                        } catch (InputMismatchException e){
                            System.out.println("Invalid entry.");
                        }
                    }
                    break;
                case 2:
                    valid = false;
                    while(!valid){
                        try {
                            tempProject.projectConsultation(projects);
                            valid = true;
                        } catch (InputMismatchException e){
                            System.out.println("Invalid entry.");
                        }
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}