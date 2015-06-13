package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.Editores;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.EditoresCollection;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.Item;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.ItemCollection;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.Lista;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.ListaCollection;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.User;




@Path("/listas")
public class ListaResource {
	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	/* GET DE TODAS LAS LISTAS */ //OK
	private String GET_LISTAS_QUERY = "select * from lista";
	
	
	@GET
	@Path("/alllistas")
	@Produces(MediaType.LIFE_API_LISTA_COLLECTION)
	public ListaCollection getListas() {
		
		ListaCollection listas = new ListaCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_LISTAS_QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Lista lista = new Lista();
				lista.setIdlista(rs.getInt("id"));
				lista.setNombre(rs.getString("nombre"));
				lista.setFecha_creacion(rs.getTimestamp("fecha_creacion").getTime());		
				lista.setUltima_modificacion(rs.getTimestamp("ultima_modificacion").getTime());
				lista.setCreador(rs.getString("creador"));
				listas.addLista(lista);
						
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return listas;
	}

	//GET LISTAS DE UN USUARIO OK
	
	private String GET_LISTASUSUARIO_QUERY = "select * from editores,lista where editores.idlista = lista.id and editores.username = ? order by lista.id desc";
	
	@GET
	@Produces(MediaType.LIFE_API_LISTA_COLLECTION)
	public ListaCollection getListasUsuario() {
		
		ListaCollection listas = new ListaCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_LISTASUSUARIO_QUERY);
			stmt.setString(1, security.getUserPrincipal().getName());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Lista lista = new Lista();
				lista.setIdlista(rs.getInt("id"));
				lista.setNombre(rs.getString("nombre"));
				lista.setFecha_creacion(rs.getTimestamp("fecha_creacion").getTime());		
				lista.setUltima_modificacion(rs.getTimestamp("ultima_modificacion").getTime());
				lista.setCreador(rs.getString("creador"));
				listas.addLista(lista);
						
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return listas;
	}
	
	
	//Get de lista por ID    //OK con cuidado
	
	//private String GET_LISTAS_ITEMS_QUERY = "select * from lista left join item on lista.id=item.id where lista.id=?";
	
	
	
	@GET
	@Path("/{idlista}")
	@Produces(MediaType.LIFE_API_LISTA)
	public Response getLista(@PathParam("idlista") String idlista,
			@Context Request request) {
		// Create CacheControl
		CacheControl cc = new CacheControl();
		
	
		Editores editor = getEditorbyusername(idlista);
		
		validateEditor(editor);
		Lista lista = getListaFromDatabase(idlista);

		// Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Long.toString(lista.getUltima_modificacion()));

		// Verify if it matched with etag available in http request
		Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
		if (rb != null) {
			return rb.cacheControl(cc).tag(eTag).build();
		}

		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		rb = Response.ok(lista).cacheControl(cc).tag(eTag);

		return rb.build();
	}
	
	
	
	
	//POST LISTA NUEVA
	
	/* AÑADIR LISTA A LA COLECCION DE LISTAS */ //OK a medias falta añadir la relacion
	private String INSERT_LISTA_QUERY = "insert into lista (nombre, creador) values (?, ?)";

	@POST
	@Consumes(MediaType.LIFE_API_LISTA)
	@Produces(MediaType.LIFE_API_LISTA)
	public Lista createLista(Lista lista) {
		
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_LISTA_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, lista.getNombre());
			stmt.setString(2, security.getUserPrincipal().getName());
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		añadirEditor(getListaFromDatabaseName(lista.getNombre()).getIdlista());
		
		
		return lista;
		
	}
	
	/* BORRAR UNA LISTA*/  //OK

	private String DELETE_LISTA_QUERY = "delete from lista where lista.id=?";

	@DELETE
	@Path("/{idlista}")
	public void deleteLista(@PathParam("idlista") String id) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

	
		Lista lista = new Lista();
		lista = getListaFromDatabase(id);
		validateCreador(lista);
			
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_LISTA_QUERY);
			stmt.setInt(1, Integer.valueOf(id));
			
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("No hay ninguna lista con id= "
						+ id);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		

}
		

	
	private String GET_LISTA_BY_NAME_QUERY = "select * from lista where lista.nombre=?";

	public Lista getListaFromDatabaseName(String nombre) {
		Lista lista = new Lista();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_LISTA_BY_NAME_QUERY);
			stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				lista.setIdlista(rs.getInt("id"));
				lista.setNombre(rs.getString("nombre"));
				lista.setFecha_creacion(rs.getTimestamp("fecha_creacion").getTime());
				lista.setUltima_modificacion(rs.getTimestamp("ultima_modificacion").getTime());
				lista.setCreador(rs.getString("creador"));
			} else {
				throw new NotFoundException("No hay ninguna lista con nombre= "
						+ nombre);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	private String GET_LISTA_BY_ID_QUERY = "select * from lista where lista.id=?";

	public Lista getListaFromDatabase(String idlista) {
		Lista lista = new Lista();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_LISTA_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(idlista));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				lista.setIdlista(rs.getInt("id"));
				lista.setNombre(rs.getString("nombre"));
				lista.setFecha_creacion(rs.getTimestamp("fecha_creacion").getTime());
				lista.setUltima_modificacion(rs.getTimestamp("ultima_modificacion").getTime());
				lista.setCreador(rs.getString("creador"));
			} else {
				throw new NotFoundException("No hay ninguna lista con id= "
						+ idlista);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	private void validateCreador(Lista lista){
		
	if (!(security.getUserPrincipal().getName().equals(lista.getCreador())))
		throw new ForbiddenException("No eres el creador de la lista.");
		
	}
	
	private void validateEditor(Editores editor){
		
		if (!(security.getUserPrincipal().getName().equals(editor.getUsername()))){
			throw new ForbiddenException("No eres editor de la lista.");
		}
			
			
	}


private String INSERT_EDITOR_QUERY = "insert into editores(username, idlista) values (?,?)";


public void añadirEditor(int idlista) {
	System.out.println(idlista);

	Connection conn = null;
	try {
		conn = ds.getConnection();
	} catch (SQLException e) {
		throw new ServerErrorException("Could not connect to the database",
				Response.Status.SERVICE_UNAVAILABLE);
	}

	PreparedStatement stmt = null;
	try {
		stmt = conn.prepareStatement(INSERT_EDITOR_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, security.getUserPrincipal().getName());
		stmt.setInt(2, idlista);
		stmt.executeUpdate();
		
		
	} catch (SQLException e) {
		throw new ServerErrorException(e.getMessage(),
				Response.Status.INTERNAL_SERVER_ERROR);
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			conn.close();
		} catch (SQLException e) {
		}
	}

	
}





/*
 * 
 * 
 * ITEMS
 * 
 * 
 * 
 */


//GET ITEMS DE UNA LISTA

private String GET_ITEMS_QUERY = "select * from item where item.id = ?";


@GET
@Path("/{idlista}/items")
@Produces(MediaType.LIFE_API_LISTA)
public ItemCollection getItems(@PathParam("idlista") String id) {
	ItemCollection items = new ItemCollection();
	
	Editores editor = getEditorbyusername(id);
	
	validateEditor(editor);
	Connection conn = null;
	try {
		conn = ds.getConnection();
	} catch (SQLException e) {
		throw new ServerErrorException("Could not connect to the database",
				Response.Status.SERVICE_UNAVAILABLE);
	}
	PreparedStatement stmt = null;
	try {
		stmt = conn.prepareStatement(GET_ITEMS_QUERY);
		stmt.setInt(1, Integer.valueOf(id));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Item item = new Item();
						
			item.setDescription(rs.getString("description"));
			item.setId(rs.getInt("id"));
			item.setIditem(rs.getInt("iditem"));
			items.addItem(item);
					
		} 
		
	} catch (SQLException e) {
		throw new ServerErrorException(e.getMessage(),
				Response.Status.INTERNAL_SERVER_ERROR);
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			conn.close();
		} catch (SQLException e) {
		}
	}
	return items;
}



	/* UPDATE DE UN ITEM  */
private String UPDATE_ITEM_QUERY = "update item set description=ifnull(?, description) where iditem=?";

@PUT
@Path("/{idlista}/items/{iditem}")
@Consumes(MediaType.LIFE_API_LISTA)
@Produces(MediaType.LIFE_API_LISTA)
public Item updateItem(@PathParam("idlista") String id,
		@PathParam("iditem") String iditem, Item item) {
	Connection conn = null;
	try {
		conn = ds.getConnection();
	} catch (SQLException e) {
		throw new ServerErrorException("Could not connect to the database",
				Response.Status.SERVICE_UNAVAILABLE);
	}
	PreparedStatement stmt = null;
	try {
		stmt = conn.prepareStatement(UPDATE_ITEM_QUERY);
		
		stmt.setString(1, item.getDescription());
		stmt.setInt(2, Integer.valueOf(iditem));
		int rows = stmt.executeUpdate();
	/*	if (rows == 1)
			item = getReviewReseñaId(reseñaid);
		else {
			throw new NotFoundException("There's no review with id="
					+ reseñaid);
		}*/
	} catch (SQLException e) {
		throw new ServerErrorException(e.getMessage(),
				Response.Status.INTERNAL_SERVER_ERROR);
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			conn.close();
		} catch (SQLException e) {
		}
	}
	return item;
}


//Get ITEM POR ID OK

	private String GET_ITEMS_ID_QUERY = "select * from item where item.id = ? and item.iditem  = ?";

	
	@GET
	@Path("/{idlista}/items/{iditem}")
	@Produces(MediaType.LIFE_API_LISTA)
	public Item getItem(@PathParam("idlista") String id, @PathParam("iditem") String iditem) {
		Item item = new Item();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_ITEMS_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(id));
			stmt.setInt(2, Integer.valueOf(iditem));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				
				item.setDescription(rs.getString("description"));
				item.setId(rs.getInt("id"));
				item.setIditem(rs.getInt("iditem"));
				

			} else {
				throw new NotFoundException("No hay ningún item con id= "
						+ iditem + " en esta lista");
			}
			

			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return item;
	}
	
	
//POST NUEVO ITEM OK queda validar que el usuario pertenece a la lista
	
	/* AÑADIR LISTA A LA COLECCION DE LISTAS */ //OK a medias falta añadir la relacion
	private String INSERT_ITEM_QUERY = "insert into item (description, id) values (?, ?)";

	@POST
	@Path("/{idlista}/items")
	@Consumes(MediaType.LIFE_API_LISTA)
	@Produces(MediaType.LIFE_API_LISTA)
	public Item createItem(Item item, @PathParam("idlista") String id) {
		
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_ITEM_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, item.getDescription());
			stmt.setString(2, id);
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		
		return item;
		
	}
	
	
	/* BORRAR UN ITEM*/  //OK

	private String DELETE_ITEM_QUERY = "delete from item where item.iditem=? and item.id = ?";

	@DELETE
	@Path("/{idlista}/items/{iditem}")
	public void deleteItem(@PathParam("idlista") String id, @PathParam("iditem") String iditem) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

	
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_ITEM_QUERY);
			stmt.setInt(1, Integer.valueOf(iditem));
			stmt.setInt(2, Integer.valueOf(id));
			
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("No hay ningún item con id= "
						+ iditem);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		

}
	
	
	/*
	 * 
	 * 
	 * EDITORES
	 * 
	 * 
	 */
	
	//EDITORES DE UNA LISTA OK
	
	private String GET_EDITORES_ID_QUERY = "select username from editores where editores.idlista=?";
	
	@GET
	@Path("/{idlista}/editores")
	@Produces(MediaType.LIFE_API_LISTA)
	public EditoresCollection getEditores(@PathParam("idlista") String id){
		EditoresCollection editores = new EditoresCollection();
			
			Editores editorcompr = getEditorbyusername(id);
			validateEditor(editorcompr);	
			
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
			PreparedStatement stmt = null;
			try {
				
				stmt = conn.prepareStatement(GET_EDITORES_ID_QUERY);
				stmt.setInt(1, Integer.valueOf(id));
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					
					Editores editor = new Editores();
					editor.setUsername(rs.getString("username"));
					//editor.setIdlista(rs.getInt("idlista"));
					editores.addEditor(editor);
									

				} 				

				
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
		
			}
		return editores;
		
	}
	
	
	//Get Editor por username
	
private String GET_EDITOR_BY_USERNAME_QUERY = "select username from editores where editores.idlista=? and editores.username like ?";
	

	public Editores getEditorbyusername(String id){

			Editores editor = new Editores();
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(GET_EDITOR_BY_USERNAME_QUERY);
				stmt.setInt(1, Integer.valueOf(id));	
				String nombre =  security.getUserPrincipal().getName();

				stmt.setString(2, nombre);
			
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					
					editor.setUsername(rs.getString("username"));
				} 				

				
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
		
			}
		return editor;
		
	}
	
	
	//INVITAR USUARIO
	
	private String IVITACION_QUERY = "insert into editores(username, idlista) values (?,?)";

	@POST
	@Path("/{idlista}/editores")
	@Consumes(MediaType.LIFE_API_LISTA)
	@Produces(MediaType.LIFE_API_LISTA)
	public Editores invitarUsuario(Editores editor, @PathParam("idlista") String id) {
		Lista lista = new Lista();
		lista = getListaFromDatabase(id);
		
		validateCreador(lista);
		
		getUserFromDatabase(editor.getUsername());
		
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(IVITACION_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, editor.getUsername());
			stmt.setString(2, id);
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
				
		
		return editor;
		
	}
	
	/* BORRAR UN EDITOR*/  

	private String DELETE_EDITOR_QUERY = "delete from editores where editores.username=? and editores.idlista = ?";

	@DELETE
	@Path("/{idlista}/editores")
	public void salirLista(@PathParam("idlista") String id) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

	
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_EDITOR_QUERY);
			stmt.setString(1, security.getUserPrincipal().getName());
			stmt.setInt(2, Integer.valueOf(id));
			
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("No perteneces a esta lista ");
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		

	}
	
	private String GET_USERS_BY_USERNAME_QUERY = "select username from usuario where usuario.username = ?";

	public void getUserFromDatabase(String username) {
		User user = new User();
		boolean exists = false;
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_USERS_BY_USERNAME_QUERY);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				
				user.setUsername(rs.getString("username"));
				exists = true;
			} else {
				throw new NotFoundException("No hay ningún usuario con username= "
						+ username);
				
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		
		if (exists == false)
			throw new ForbiddenException("Usuario no encontrado ");
		
		
	}

	
	
}
	
	
	

