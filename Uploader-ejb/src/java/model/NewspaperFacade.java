/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Newspaper;
import generated.EpaperRequest;
import java.io.File;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 *
 * @author Mariusz Kohut
 */
@Stateless
public class NewspaperFacade extends AbstractFacade<Newspaper> {

    private static final Logger LOGGER = Logger.getLogger(NewspaperFacade.class.getName());
            
    @PersistenceContext(unitName = "Uploader-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NewspaperFacade() {
        super(Newspaper.class);
    }
    
    public Newspaper parseSchema(File file) {
        if (validateSchema(file)) {
            return parseRequest(file);
        }
        return null;
    }
    
    private boolean validateSchema(File file) {
        boolean isValid = true;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(
                    NewspaperFacade.class.getResource("/xsd/newspaper.xsd")
            );
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (Exception e) {
            isValid =  false;
            LOGGER.info("Invalid schema uploaded. " + e.getMessage());
        }
        return isValid;
    }
    
    private Newspaper parseRequest(File file) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(EpaperRequest.class);
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            EpaperRequest epaper = (EpaperRequest) unmarshaller.unmarshal(
                    new StreamSource(file)
            );
            return mapToEntity(epaper, file);
        } catch (Exception e) {
            LOGGER.info("Could not create object from reqeust. " + e.getMessage());
            return null;
        }
    }

    private Newspaper mapToEntity(EpaperRequest epaper, File file) {
        Newspaper newNewspaper = new Newspaper();
        newNewspaper.setDpi(epaper.getDeviceInfo().getScreenInfo().getDpi().toString());
        newNewspaper.setWidth(epaper.getDeviceInfo().getScreenInfo().getWidth().toString());
        newNewspaper.setHeight(epaper.getDeviceInfo().getScreenInfo().getHeight().toString());
        newNewspaper.setNewspaperName(epaper.getDeviceInfo().getAppInfo().getNewspaperName());
        String fileName = file.getName();
        newNewspaper.setFilename(fileName);
        return newNewspaper;
    }
}
