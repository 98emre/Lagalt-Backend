package project.lagalt.service;

import project.lagalt.model.entities.Comment;
import project.lagalt.model.entities.Project;

import java.util.Set;

public interface CommentService extends CrudService<Comment,Integer> {

    public Comment addCommentToProject(int projectId, Comment comment, String username);

}
