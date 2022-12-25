package pojo;
import java.util.ArrayList;

public class CreationOrder {

    ArrayList<String> ingredients;

    public CreationOrder(ArrayList ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList ingredients) {
        this.ingredients = ingredients;
    }
}
