package net.aws.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


@ManagedBean(name="langBean", eager=true)
@SessionScoped
public class LanguageBean implements Serializable {

    private static final long serialVersionUID = 2756934361134603857L;
    private String locale;
    
    private static Map<String,Object> countries;
    
    static{
    	countries = new LinkedHashMap<String, Object>();
    	countries.put("English", Locale.ENGLISH);
    	countries.put("Română", new Locale("ro", "RO"));
    }
    
    public Map<String,Object> getCountries() {
    	return countries;
    }
    
    public String getLocale() {
    	return locale;
    }
    
    public void setLocale(String locale) {
    	this.locale = locale;
    }
    
    public void localeChanged(ValueChangeEvent e) {
    	String newLocaleValue = e.getNewValue().toString();
    	
    	for( Map.Entry<String, Object> entry : countries.entrySet() ) {
    		if( entry.getValue().toString().equals(newLocaleValue) ) {
    			FacesContext.getCurrentInstance().getViewRoot()
    				.setLocale((Locale) entry.getValue());
    		}
    	}
    }

}