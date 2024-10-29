package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.Workspace;
import utils.SQLUtils;

public class WorkspaceDAO implements interfaceDAO<Workspace> {
    private ArrayList<Workspace> list;
    private Workspace workspace;
    Connection con;

    @Override
    public ArrayList<Workspace> getAll() {
        list = null;
        con = SQLUtils.getConnection();
        if (con != null) {
            try {
                String query = "SELECT * FROM Workspaces WHERE Archived = 0";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Workspace workspace = new Workspace();
                    workspace.setWorkspaceId(rs.getInt("WorkspaceID"));
                    workspace.setPin(rs.getInt("Pin"));
                    workspace.setWorkspaceName(rs.getString("Name"));
                    workspace.setArchive(rs.getBoolean("Archived"));
                    list.add(workspace);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SQLUtils.closeConnection(con);
            }
        }
        return list;
    }

    @Override
    public boolean create(Workspace t) {
        boolean b = false;
        con = SQLUtils.getConnection();
        if (con != null) {
            try {
                String query = "INSERT INTO Workspaces (Name, Pin, Archived) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, workspace.getWorkspaceName());
                ps.setInt(2, workspace.getPin());
                ps.setBoolean(3, workspace.isArchive());
                b = ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SQLUtils.closeConnection(con);
            }
        }
        return b;
    }

    @Override
    public Workspace getByID(int t) {
        con = SQLUtils.getConnection();
        if (con != null) {
            try {
                String query = "SELECT * FROM Workspaces WHERE WorkspaceID = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, t);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    workspace = new Workspace();
                    workspace.setWorkspaceId(rs.getInt("WorkspaceID"));
                    workspace.setPin(rs.getInt("Pin"));
                    workspace.setWorkspaceName(rs.getString("Name"));
                    workspace.setArchive(rs.getBoolean("Archived"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SQLUtils.closeConnection(con);
            }
        }
        return workspace;
    }

    @Override
    public boolean update(Workspace t) {
        boolean b = false;
        con = SQLUtils.getConnection();
        if (con != null) {
            try {
                String query = "UPDATE Workspaces SET Name = ?, Pin = ?, Archived = ? WHERE WorkspaceID = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, workspace.getWorkspaceName());
                ps.setInt(2, workspace.getPin());
                ps.setBoolean(3, workspace.isArchive());
                ps.setInt(4, workspace.getWorkspaceId());
                b = ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SQLUtils.closeConnection(con);
            }
        }
        return b;
    }

    @Override
    public boolean delete(int t) {
        boolean b = false;
        con = SQLUtils.getConnection();
        if (con != null) {
            try {
                String query = "UPDATE Workspaces SET Archived = 1 WHERE WorkspaceID = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, t);
                b = ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SQLUtils.closeConnection(con);
            }
        }
        return b;
    }

}
