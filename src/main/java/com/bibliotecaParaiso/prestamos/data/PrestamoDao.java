package com.bibliotecaParaiso.prestamos.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotecaParaiso.prestamos.domain.Historial;
import com.bibliotecaParaiso.prestamos.domain.Libro;
import com.bibliotecaParaiso.prestamos.domain.Prestamo;
import com.bibliotecaParaiso.prestamos.domain.Usuario;

@Repository
public class PrestamoDao {
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallPrestamo;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallPrestamo = new SimpleJdbcCall(dataSource).withProcedureName("InsertarPrestamo");
	}

	public boolean solicitarPrestamo() {
		return false;
	}
	
	public List<Usuario> listaUsuarios(){
		List<Usuario> usuarios = new ArrayList<>();
		
		String selectSql = "execute muestraUsuarios";
		jdbcTemplate
				.query(selectSql, new Object[] {},
						(rs, row) -> new Usuario(
								rs.getInt("codigo"),
								rs.getString("cedula")))
				.forEach(entry -> usuarios.add(entry));
		
		return usuarios;
	}
	
	public List<Libro> listaLibros(){
		List<Libro> libros = new ArrayList<>();
		
		String selectSql = "execute muestraLibrosDisponibles";
		jdbcTemplate
				.query(selectSql, new Object[] {},
						(rs, row) -> new Libro(rs.getInt("codigo"),
												rs.getString("titulo")))
				.forEach(entry -> libros.add(entry));
		
		return libros;
	}
	
	@Transactional
	 public Prestamo insertarPrestamo(Prestamo prestamo) throws SQLException{
		
		
	  SqlParameterSource parameterSource = new MapSqlParameterSource()
	    .addValue("codUsuario", prestamo.getUsuario().getCodigoUsuario())
	    .addValue("codLibro", prestamo.getLibro().getCodigo());
	  
	  Map<String, Object> outParameters = simpleJdbcCallPrestamo.execute(parameterSource);
	  
	  prestamo.setCodigo(Integer.parseInt(outParameters.get("codPrestamo").toString()));
	  
	  
	  
	  return prestamo;
	 }
}
