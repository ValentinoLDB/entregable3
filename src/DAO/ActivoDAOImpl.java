package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Interfaces.ActivoDAO;
import Modelo.Activo;

public class ActivoDAOImpl implements ActivoDAO {

    @Override
    public void crear(Activo activo) {
        String sql = "INSERT INTO ACTIVO (ID_USUARIO, ID_MONEDA, CANTIDAD) VALUES (?, ?, ?)";
        Connection connection = ConexionBD.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, activo.getIDUsuario());
            pstmt.setInt(2, activo.getIDMoneda());
            pstmt.setDouble(3, activo.getCantidad());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Activo> listar() {
        List<Activo> activos = new ArrayList<>();
        String sql = """
                SELECT 
                    A.ID, A.CANTIDAD, 
                    A.ID_USUARIO, A.ID_MONEDA
                FROM ACTIVO A
                """;
        Connection connection = ConexionBD.getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Activo activo = new Activo();
                activo.setID(rs.getInt("ID"));
                activo.setCantidad(rs.getDouble("CANTIDAD"));
                activo.setIDUsuario(rs.getInt("ID_USUARIO"));
                activo.setIDMoneda(rs.getInt("ID_MONEDA"));
                activos.add(activo);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return activos;
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = "UPDATE ACTIVO SET CANTIDAD = ?, ID_USUARIO = ?, ID_MONEDA = ? WHERE ID = ?";
        Connection connection = ConexionBD.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, activo.getCantidad());
            pstmt.setInt(2, activo.getIDUsuario());
            pstmt.setInt(3, activo.getIDMoneda());
            pstmt.setInt(4, activo.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Activo obtener(int id) {
        String sql = """
                SELECT 
                    A.ID, A.CANTIDAD, 
                    A.ID_USUARIO, A.ID_MONEDA
                FROM ACTIVO A
                WHERE A.ID = ?
                """;
        Connection connection = ConexionBD.getConnection();
        Activo activo = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    activo = new Activo();
                    activo.setID(rs.getInt("ID"));
                    activo.setCantidad(rs.getDouble("CANTIDAD"));
                    activo.setIDUsuario(rs.getInt("ID_USUARIO"));
                    activo.setIDMoneda(rs.getInt("ID_MONEDA"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return activo;
    }
}
