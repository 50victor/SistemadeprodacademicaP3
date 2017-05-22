
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Publication extends AcademicProduction{
    private String publishingConferenceName;
    private Project associatedTo;

    static private int numberOfPublications = 0;

    private Publication(String name, String conference, int yearPublished, Project project){
        this.name = name;
        this.publishingConferenceName = conference;
        this.year = yearPublished;
        this.associatedTo = project;
    }

    static int getNumberOfPublications() {
        return numberOfPublications;
    }

    static Publication createPublication(ArrayList<Project> projects, ArrayList<Integer> collabsIndexes,
                                         ArrayList<Collaborator> collaborators) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        Project temp = new Project();
        ArrayList<Integer> list;

        System.out.println("Insert the publication's name.");
        String name = scan.nextLine();
        System.out.println("Insert the name of the conference which it was publicated.");
        String conference = scan.nextLine();
        System.out.println("Insert the year of the publication.");
        int year;
        try{
            year = scan.nextInt();
        } catch (Exception InputMismatchException){
            System.out.println("Invalid year entry. Publication not registered.");
            return null;
        }

        list = temp.showInProgressProjects(projects); //Lista que vai guardar os índices dos projetos em andamento
        System.out.println("Ongoing projects: ");
        for(Integer aux : list){
            int pos = aux.intValue();
            System.out.println(pos + ". " + projects.get(pos).getProjectName());
        }
        System.out.println("Choose which project you want to associate with your publication.");
        int opt = scan.nextInt();
        if(list.contains(opt)){
            temp = projects.get(opt);
        } else {
            System.out.println("Invalid option. Publication not registered.");
            return null;
        }
        Publication publication = new Publication(name, conference, year, temp);
        publication.addPublication(projects.get(opt), publication); //Colocando a publicação no projeto

        for(Integer aux : collabsIndexes){
            int pos = aux.intValue();
            publication.addPublication(collaborators.get(pos), publication); //Colocando a publicação no colaborador
        }
        numberOfPublications++;
        return publication;
    }

    private void addPublication(Collaborator collaborator, Publication publication){
        boolean added = false;
        for(int i = 0; i < collaborator.publications.size(); i++){
            if(publication.year >= collaborator.publications.get(i).year){
                collaborator.publications.add(i, publication);
                added = true;
            }
        }
        if(!added){
            collaborator.publications.add(publication);
        }
    }

    private void addPublication(Project project, Publication publication){
        boolean added = false;
        for(int i = 0; i < project.publications.size(); i++){
            if(publication.year >= project.publications.get(i).year){
                project.publications.add(i, publication);
                added = true;
            }
        }
        if(!added){
            project.publications.add(publication);
        }
    }
}
