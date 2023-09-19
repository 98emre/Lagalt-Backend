package project.lagalt.service;

import java.util.Collection;
import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;

public interface ProjectService extends CrudService<Project,Integer> {
    Collection<Project> findAllByTitle(String title);
    Collection<Collaborator> findAllPendingByCollaborator(Project project);
    Collection<Collaborator> findAllApprovedByCollaborator(Project project);

    boolean findCollaboratorExist(Integer ProjectId, User user);

}
