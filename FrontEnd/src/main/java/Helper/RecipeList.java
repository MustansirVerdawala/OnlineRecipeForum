package Helper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;

/**
 *
 * @author mustansir
 */
@XmlRootElement(name = "recipes") // Root element for JAXB
@XmlAccessorType(XmlAccessType.FIELD)  // Use fields directly for marshalling/unmarshalling
public class RecipeList {
    
    // No need to use @XmlElement on the field because we are using XmlAccessType.FIELD
    @XmlElement(name = "recipe") // Root element for JAXB
    private ArrayList<Recipe> recipes;

    public RecipeList(){
    }
    
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
