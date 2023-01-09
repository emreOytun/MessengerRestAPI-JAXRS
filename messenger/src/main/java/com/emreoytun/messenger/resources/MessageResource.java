package com.emreoytun.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.emreoytun.messenger.entity.Message;
import com.emreoytun.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON) // Specifies the request body type.
@Produces(MediaType.APPLICATION_JSON) // Specifies the response type.
public class MessageResource {

	private final MessageService messageService = new MessageService(); 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@QueryParam("year") int year,
									 @QueryParam("start") int start,
									 @QueryParam("size") int size) {
		
		// If query parameter is not entered in the url, then year will be 0. Query parameters are initailized with their default values.
		if (year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if (start > 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
	
		return messageService.getAllMessages();
	}
	
	/* @Context annotation is used to get a header value. */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build(); // uriInfo.getAbsoulePath(localhost:8080/messenger/webabi/messages). 
																		// By getting the absolute path in a path builder, we can directly add a new path which is newId to the URI. 
		
		/* We can build the Response in that way. 
		return Response.status(Status.CREATED)
				.entity(newMessage)
				.build();
		*/
		
		/* This way is better, because it directly sets status to '201' and make a location header for the new resource that has just created. */
		return Response.created(uri)
						.entity(newMessage)
						.build();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id) {
		return messageService.getMessage(id);
	}
		
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	/* When this path matches, JAX-RS/Jersey sees that this method returns a new resource; then it goes inside this resource and finds the method. */
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
