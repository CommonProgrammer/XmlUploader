/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mariusz Kohut
 */
@Entity
@Table(name = "newspaper")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Newspaper.findAll", query = "SELECT n FROM Newspaper n")
    , @NamedQuery(name = "Newspaper.findByIdnewspaper", query = "SELECT n FROM Newspaper n WHERE n.idnewspaper = :idnewspaper")
    , @NamedQuery(name = "Newspaper.findByNewspaperName", query = "SELECT n FROM Newspaper n WHERE n.newspaperName = :newspaperName")
    , @NamedQuery(name = "Newspaper.findByHeight", query = "SELECT n FROM Newspaper n WHERE n.height = :height")
    , @NamedQuery(name = "Newspaper.findByWidth", query = "SELECT n FROM Newspaper n WHERE n.width = :width")
    , @NamedQuery(name = "Newspaper.findByDpi", query = "SELECT n FROM Newspaper n WHERE n.dpi = :dpi")})
public class Newspaper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idnewspaper")
    private Long idnewspaper;
    @Column(name = "newspaperName")
    private String newspaperName;
    @Column(name = "height")
    private String height;
    @Column(name = "width")
    private String width;
    @Column(name = "dpi")
    private String dpi;
    @Column(name = "filename")
    private String filename;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "insert_date", updatable = false)
    private Date insertDate;
  
    public Newspaper() {
        this.insertDate = new Date();
    }

    public Long getIdnewspaper() {
        return idnewspaper;
    }

    public void setIdnewspaper(Long idnewspaper) {
        this.idnewspaper = idnewspaper;
    }

    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public Date getInsertDate() {
            return insertDate;
    }
}
