/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Newspaper;
import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.NewspaperFacade;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mariusz Kohut
 */
@Named(value = "newspaperController")
@SessionScoped
@ManagedBean
public class NewspaperController implements Serializable {

    @EJB
    private NewspaperFacade newspaperFacade;

    private UploadedFile file;
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public NewspaperController() {
    }
    
    public List<Newspaper> findAll() {
        return this.newspaperFacade.findAll();
    }
    
    public void upload() {
        if(file != null) {
            File uploaded = new File(file.getFileName());
            Newspaper valid = newspaperFacade.parseSchema(uploaded);
            if (valid != null) {
                this.newspaperFacade.create(valid);
                FacesMessage message = new FacesMessage("Succesful", valid.getFilename() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage("Invalid schema uploaded");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Failed to upload file, try again later");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
