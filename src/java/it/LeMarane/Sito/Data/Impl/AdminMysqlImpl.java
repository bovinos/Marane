package it.LeMarane.Sito.Data.Impl;

import it.LeMarane.Sito.Data.Model.Admin;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alex
 */
public class AdminMysqlImpl implements Admin {

    private int ID;
    private String username;
    private String password;
    protected boolean dirty;

    public AdminMysqlImpl() {
        this.ID = 0;
        this.username = "";
        this.password = "";
        this.dirty = false;
    }

    public AdminMysqlImpl(ResultSet rs) throws SQLException {
        this();
        this.ID = rs.getInt("ID");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        this.dirty = true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
        
    }

}
