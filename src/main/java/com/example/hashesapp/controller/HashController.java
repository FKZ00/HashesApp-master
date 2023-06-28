package com.example.hashesapp.controller;

import com.example.hashesapp.model.Hash;
import com.example.hashesapp.service.HashService;
import com.example.hashesapp.utils.FileUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import javax.json.Json;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/hash")
public class HashController {
  @Inject
  private HashService hashService;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveHash(@NotNull @Valid Hash hash) {
    var json = Json.createObjectBuilder();
    try {
      Hash createdHash = hashService.saveHash(hash);
      return Response.status(201).entity(createdHash).build();
    } catch (Exception e) {
      return Response.status(400)
          .entity(
              json.add("error", e.getMessage())
                  .build()).build();
    }
  }

  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllHashes() {
    var json = Json.createObjectBuilder();
    try {
      List<Hash> allHashes = hashService.getAllHashes();
      return Response.status(200).entity(allHashes).build();
    } catch (Exception e) {
      return Response.status(400)
          .entity(json.add("error", e.getMessage()).build()).build();
    }
  }

  @DELETE
  @Path("/id/{id}")
  public Response removeHash(@PathParam("id") String id) {
    var json = Json.createObjectBuilder();
    try {
      UUID uuid = UUID.fromString(id);
      hashService.removeHash(uuid);
      return Response.status(200).build();
    } catch (Exception e) {
      return Response.status(400)
          .entity(json.add("error", e.getMessage()).build()).build();
    }
  }

  @POST
  @Path("/generate")
  @Produces(MediaType.APPLICATION_JSON)
  public Response generateHash(@FormDataParam("file") InputStream fileInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileMetaData) {
    var json = Json.createObjectBuilder();
    byte[] file = FileUtils.readFileFromFileInputStream(fileInputStream, fileMetaData);
    Hash createdHash = hashService.generateHash(file);
    return Response.status(200).entity(createdHash).build();
  }
}
