package project.lagalt.service;

import project.lagalt.model.entities.Collaborator;
import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;
import project.lagalt.model.entities.User;

import java.util.Collection;
import java.util.Set;

public interface ProjectService extends CrudService<Project,Integer> {
    Collection<Project> findAllByTitle(String title);
    Collection<Collaborator> findAllPendingByCollaborator(Project project);
    Collection<Collaborator> findAllApprovedByCollaborator(Project project);

}
