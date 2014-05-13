package it.LeMarane.Sito.Data.Impl;

import it.LeMarane.Sito.Data.Model.Image;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alex
 */
public class ImageMysqlImpl implements Image {

    private int ID;
    private String URL;
    private String description;
    private String name;
    private boolean banner;
    protected SitoDataLayerMysqlImpl dataLayer;
    protected boolean dirty;

    public ImageMysqlImpl(SitoDataLayerMysqlImpl dataLayer) {
        this.dataLayer = dataLayer;
        this.ID = 0;
        this.URL = "";
        this.description = "";
        this.name = "";
        this.banner = false;
        this.dirty = false;
    }

    public ImageMysqlImpl(SitoDataLayerMysqlImpl dataLayer, ResultSet rs) throws SQLException {
        this(dataLayer);
        this.ID = rs.getInt("ID");
        this.URL = rs.getString("URL");
        this.description = rs.getString("description");
        this.name = rs.getString("name");
        this.banner = rs.getBoolean("banner");
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getURL() {
        return this.URL;
    }

    @Override
    public void setURL(String URL) {
        this.URL = URL;
        this.dirty = true;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        this.dirty = true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    @Override
    public boolean isBanner() {
        return this.banner;
    }

    @Override
    public void setBanner(boolean banner) {
        this.banner = banner;
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

    @Override
    public void copyFrom(Image image) {
        this.ID = image.getID();
        this.URL = image.getURL();
        this.description = image.getDescription();
        this.name = image.getName();
        this.banner = image.isBanner();
        this.dirty = true;
    }

}
