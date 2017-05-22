
import java.util.*;

class Project {
    private String projectName;
    private String objective;
    private String description;

    private String funderAgency;
    private float fundingValue;

    private Calendar beginDate;
    private Calendar endDate;

    private ArrayList<Collaborator> collaborators = new ArrayList<>();
    ArrayList<Publication> publications = new ArrayList<>(3);

    private int statusPos; //0. Em elaboração; 1. Em andamento; 2. Concluído

    Project(String name, String objective, Professor professor,
                   String description, String funderAgency, float fundingValue) {
        this.projectName = name;
        this.objective = objective;
        this.collaborators.add(0, professor);
        this.statusPos = 0;
        this.description = description;
        this.funderAgency = funderAgency;
        this.fundingValue = fundingValue;
    }

    Project(){

    }


    ArrayList<Project> projectCreator(ArrayList<Project> projects, ArrayList<Collaborator> collaborators) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        Professor responsibleProfessor;
        System.out.println("Please, insert the name of the project.");
        String name = scan.nextLine();
        if(searchProject(projects, name) != -1){
            System.out.println("There is already a project with that name.");
            return null;
        }
        System.out.println("Please, insert the objective of the project.");
        String objective = scan.nextLine();

        System.out.println("Please, insert a briefing of the project.");
        String description = scan.nextLine();

        System.out.println("Please. insert a professor's name.");
        String profName = scan.nextLine();
        int index = Collaborator.searchCollaborator(collaborators, profName);
        if(index == -1){
            System.out.println("Professor not found. A project cannot be registered without a professor.");
            return null;
        } else {
            if(collaborators.get(index) instanceof Professor){
                responsibleProfessor = (Professor)collaborators.get(index);
            } else {
                System.out.println("Not a professor. Project not registered.");
                return null;
            }
        }

        System.out.println("Please inform the funding agency.");
        String funderAgency = scan.nextLine();
        System.out.println("Please input the funding value.");
        float fundingValue = scan.nextFloat();
        if(fundingValue < 0){
            System.out.println("Invalid funding value. Project not registered.");
            return projects;
        }

        if(responsibleProfessor != null){
            Project temp = new Project(name, objective, responsibleProfessor,
                    description, funderAgency, fundingValue);
            projects.add(temp);
            responsibleProfessor.projects.add(temp);
        } else {
            System.out.println("Project creation error.");
        }
        return projects;
    }

    /* RECEBE o array de projects em que será buscado
     * e a String com o nome a ser buscado.
     * RETORNA a posição em que a String foi primeiro encontrada
     * se nenhuma ocorrência for encontrada, retorna -1
     */
    static private int searchProject(ArrayList<Project> projectList, String searched){
        int position = -1;
        if(projectList == null) return -1;
        for(int i = 0; i < projectList.size(); i++){
            if(projectList.get(i).projectName.equals(searched)){
                position = i;
            }
        }
        return position;
    }

    ArrayList<Project> editProject(ArrayList<Collaborator> collaborators, ArrayList<Project> projects) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose projects.");
        showProjectList(projects);
        int index = scan.nextInt();
        if(index > projects.size() || index < 0){
            System.out.println("Invalid input.");
            return projects;
        }
        System.out.println(projects.get(index).projectName + " selected.");
        System.out.println("1. Change project status.\n2. Alocate colaborators.");
        int option = scan.nextInt();
        switch(option){
            case 1:
                projects = changeProjectStatus(projects, index);
                break;
            case 2:
                if(projects.get(index).getStatusPos() == 0){
                    projects.get(index).collaborators = allocateCollaborator(collaborators, projects.get(index));
                } else {
                    System.out.println("It is only possible to allocate on On elaboration projects.");
                }
                break;
            default:
                System.out.println("No changes made.");
                break;
        }
        return projects;
    }


    private ArrayList<Collaborator> allocateCollaborator(ArrayList<Collaborator> collaborators, Project project) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        Collaborator temp = new Collaborator();
        System.out.println("Choose colaborator.");
        temp.showCollaboratorsList(collaborators);
        int index = scan.nextInt();
        if(index < 0 || index > collaborators.size()){
            System.out.println("Invalid.");
            return collaborators;
        } else {
            if(collaborators.get(index) instanceof Professor){
                if(collaborators.get(index).projects.contains(project)){
                    System.out.println("This professor is already in this project.");
                } else {
                    project.collaborators.add(0, collaborators.get(index));
                    ((Professor)collaborators.get(index)).projects.add(project);
                    System.out.println("Professor alocated.");
                }
            } else {
                if(collaborators.get(index).projects.size() > 0){
                    System.out.println("This colaborator is already alocated.");
                } else {
                    project.collaborators.add(collaborators.get(index));
                    collaborators.get(index).projects.add(project);
                    System.out.println("Colaborator alocated.");
                }
            }
        }
        return collaborators;
    }

    private Calendar setDate(int day, int month, int year){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);

        return date;
    }

    private ArrayList<Project> changeProjectStatus(ArrayList<Project> projects, int index) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        switch(projects.get(index).statusPos){
            case 0:
                System.out.println("Project ongoing at the moment...");
                System.out.println("Changing project status from On elaboration to Ongoing.");
                System.out.println("Please insert the project's beggining date in the following layout: dd mm yyyy");
                try{
                    int day = scan.nextInt();
                    int month = scan.nextInt();
                    int year = scan.nextInt();
                    projects.get(index).beginDate = setDate(day, month, year);
                    projects.get(index).statusPos = 1;
                } catch (Exception InputMismatchException){
                    System.out.println("Day, Month and Year must be integers. " +
                            "dd mm yyyy\nProject not added.");
                }
                break;
            case 1:
                System.out.println("Project ongoing at the moment...");
                if(projects.get(index).publications.size() != 0){
                    System.out.println("Changing project status from Ongoing to Finished.");
                    System.out.println("Please insert the project's finish date in the following layout: dd mm yyyy");
                    try{
                        Calendar date;
                        int day = scan.nextInt();
                        int month = scan.nextInt();
                        int year = scan.nextInt();
                        date = setDate(day, month, year);
                        if(projects.get(index).beginDate.compareTo(date) < 0){
                            projects.get(index).endDate = date;
                        } else {
                            System.out.println("Finish date can't happen before the creation date.");
                            return projects;
                        }
                        projects.get(index).statusPos = 2;
                    } catch (Exception InputMismatchException){
                        System.out.println("Day, Month and Year must be integers. " +
                                "dd mm yyyy\nProject not added.");
                    }
                } else {
                    System.out.println("A project can only be finished when there are publications associated with it.");
                }
                break;
        }
        return projects;
    }

    void labReport(ArrayList<Collaborator> collaborators, ArrayList<Project> projects){
        System.out.println("Number of Colaborators: " + collaborators.size());

        int status0counter = 0; //em elaboração
        int status1counter = 0; //em andamento
        int status2counter = 0; //concluído
        for (Project project : projects) {
            if (project.statusPos == 0) status0counter++;
            if (project.statusPos == 1) status1counter++;
            if (project.statusPos == 2) status2counter++;
        }
        System.out.println("On elaboration projects: "+status0counter);
        System.out.println("Ongoing projects: "+status1counter);
        System.out.println("Finished projects: "+status2counter);

        System.out.println("Total number of projects: "+projects.size());

        System.out.println("Number of orientations: " + Orientation.getNumberOfOrientations());
        System.out.println("Number of publications: " + Publication.getNumberOfPublications());
    }

    void projectConsultation(ArrayList<Project> projects) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        boolean valid = false;
        System.out.println("Choose Project. Type -1 to return.");
        showProjectList(projects);
        while(!valid){
            int opt = scan.nextInt();
            if(opt < -1 || opt > projects.size()){
                System.out.println("Invalid option.");
                valid = false;
            } else {
                if(opt == -1){
                    return;
                } else {
                    displayProjectInfo(projects.get(opt));
                }
                valid = true;
            }
        }
    }

    private void displayProjectInfo(Project project){
        System.out.println("Name: "+ project.projectName);
        System.out.println("Description: "+ project.description);
        System.out.println("Objective: "+ project.objective);
        System.out.println("Funder: "+ project.funderAgency);
        System.out.println("Fund value: "+ project.fundingValue);
        switch(project.getStatusPos()){
            case 0:
                System.out.println("Status: On elaboration");
                break;
            case 1:
                System.out.println("Status: Ongoing");
                break;
            case 2:
                System.out.println("Status: Finished");
                break;
            default:
                break;
        }
        if(project.statusPos > 0){ // > 0 pois com 1 ou com 2 é necessário mostrar a data de início.
            System.out.println("Beggining: "+ formattedDate(project.beginDate));
        }
        if(project.statusPos == 2){ // == 2 pois apenas com 2 é necessário msotrar a data de término.
            System.out.println("Ending: "+ formattedDate(project.endDate));
        }
        if(project.publications.size() > 0){
            System.out.print("Publications: ");
            for(Publication pub : project.publications){
                System.out.print(pub.name +"(" + pub.year + ") | ");
            }
        }
        if(project.collaborators.size() > 0) {
            System.out.print("Colaboradores: ");
            Collaborator collab = new Collaborator();
            collab.printCollaborators(project.collaborators);
        }
    }

    private String formattedDate(Calendar date){
        String temp = "";
        temp += date.get(Calendar.DAY_OF_MONTH) + "/";
        temp += date.get(Calendar.MONTH) + "/";
        temp += date.get(Calendar.YEAR);

        return temp;
    }

    String getProjectName() {
        return projectName;
    }

    ArrayList<Integer> showInProgressProjects(ArrayList<Project> projects){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++){
            if(projects.get(i).statusPos == 1){
                list.add(i);
            }
        }
        return list;
    }

    private void showProjectList(ArrayList<Project> projects){
        for(int i = 0; i < projects.size(); i++){
            System.out.println(i + ". " + projects.get(i).projectName);
        }
    }

    int getStatusPos() {
        return statusPos;
    }

}
