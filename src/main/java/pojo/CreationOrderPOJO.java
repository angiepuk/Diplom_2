package pojo;
import java.util.ArrayList;

public class CreationOrderPOJO {

    ArrayList<String> ingredients;

    public CreationOrderPOJO(ArrayList ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList ingredients) {
        this.ingredients = ingredients;
    }
}
