package Modelo.Interfaces;

import Modelo.Entidad.Usuario;

public interface UsuarioDAO extends CRUD<Usuario> {
	Usuario obtener(String email);
}
