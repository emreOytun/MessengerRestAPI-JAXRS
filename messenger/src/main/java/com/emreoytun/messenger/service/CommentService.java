package com.emreoytun.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.emreoytun.messenger.dao.DatabaseClass;
import com.emreoytun.messenger.entity.Comment;
import com.emreoytun.messenger.entity.Message;
import com.emreoytun.messenger.messages.ErrorMessage;

public class CommentService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	/* NOTE: It's not a good approach to have all these response things in the Service; because service is not expected to do that.  */
	/* NOTE: Exceptions below can be thrown in the CommentResource, it's better than this approach but writing an exception mapper is more better. */
	public Comment getComment(long messageId, long commentId){
		ErrorMessage errorMessage = new ErrorMessage("Not found", 404, "http://emreoytun.org");
		Response response = Response.status(Status.NOT_FOUND)
		.entity(errorMessage)
		.build();
		
		Message message = messages.get(messageId);
		if (message == null) {
			// WebApplicationException is automatically handled/mapped by Jersey, so it can be used as custom exception.
			throw new WebApplicationException(response);
		}
		
		Map<Long, Comment> comments = message.getComments(); 
		Comment comment = comments.get(commentId);
		if (comment == null) {
			throw new WebApplicationException(response);
		}
		
		return comment;
	}
	
	public Comment addComent(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if (comment.getId() <= 0){
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}