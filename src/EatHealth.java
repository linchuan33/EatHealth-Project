import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class EatHealth extends Application {
   private List<String> meatOptions = new ArrayList<>(Arrays.asList("Chicken", "Beef", "Fish", "Shellfish", "Duck", "Lamb"));
   private List<String> carbohydrateOptions = new ArrayList<>(Arrays.asList("Rice", "Noodle"));
   private List<String> vegetablesOptions = new ArrayList<>(Arrays.asList("Potatoes", "Tomatoes", "Carrots", "Lettuce", "Cucumbers", "Mushrooms"));


   private Button pickNowButton;
   private VBox meatBox, carbohydrateBox, vegetablesBox;


   public static void main(String[] args) {
       launch(args);
   }


   @Override
   public void start(Stage primaryStage) {
       BorderPane root = new BorderPane();
       root.setCenter(createOptionsPane());
       root.setBottom(createPickNowButton());


       Scene scene = new Scene(root, 500, 400);
       primaryStage.setTitle("EatHealth");
       primaryStage.setScene(scene);
       primaryStage.show();
   }


   private VBox createOptionsPane() {
       meatBox = createFoodBox("Meat", meatOptions);
       carbohydrateBox = createFoodBox("Carbohydrate", carbohydrateOptions);
       vegetablesBox = createFoodBox("Vegetables", vegetablesOptions);


       VBox optionsPane = new VBox(createAddButton(meatOptions), meatBox, createAddButton(carbohydrateOptions), carbohydrateBox, createAddButton(vegetablesOptions), vegetablesBox);
       optionsPane.setAlignment(Pos.CENTER);
       optionsPane.setSpacing(20);
       optionsPane.setFillWidth(true); // Allow children to grow horizontally
       optionsPane.setPadding(new Insets(20));


       return optionsPane;
   }


   private VBox createFoodBox(String category, List<String> options) {
       VBox foodBox = new VBox();
       foodBox.setAlignment(Pos.CENTER);
       foodBox.setSpacing(10);


       Label categoryLabel = new Label(category + ":");
       categoryLabel.setStyle("-fx-font-size: 14;");
       foodBox.getChildren().add(categoryLabel);


       for (String option : options) {
           HBox optionBox = createOptionBox(option, category, options);
           foodBox.getChildren().add(optionBox);
       }


       return foodBox;
   }


   private HBox createOptionBox(String option, String category, List<String> options) {
       HBox optionBox = new HBox();
       optionBox.setAlignment(Pos.CENTER_LEFT);


       Button deleteButton = createDeleteButton(option, category, options);


       Label optionLabel = new Label(option);
       optionLabel.setStyle("-fx-border-color: black; -fx-padding: 5px;");
       optionLabel.setStyle("-fx-font-size: 12;");


       optionBox.getChildren().addAll(deleteButton, optionLabel);
       return optionBox;
   }


   private Button createDeleteButton(String option, String category, List<String> options) {
       Button deleteButton = new Button("-");
       deleteButton.setOnAction(e -> {
           if (options.remove(option)) {
               updateOptionsPane();
           }
       });


       return deleteButton;
   }


   private Button createAddButton(List<String> options) {
       Button addButton = new Button("+");
       addButton.setOnAction(e -> {
           TextInputDialog dialog = new TextInputDialog();
           dialog.setTitle("Add Option");
           dialog.setHeaderText("Enter the new option:");
           dialog.setContentText("Option:");


           dialog.showAndWait().ifPresent(newOption -> {
               options.add(newOption);
               updateOptionsPane();
           });
       });


       return addButton;
   }


   private Button createPickNowButton() {
       pickNowButton = new Button("Pick Now");
       pickNowButton.setOnAction(e -> pickNow());


       VBox pickNowBox = new VBox(pickNowButton);
       pickNowBox.setAlignment(Pos.CENTER);
       pickNowBox.setPadding(new Insets(20, 0, 0, 0));


       return pickNowButton;
   }


   private void pickNow() {
       String selectedMeat = pickRandomOption(meatOptions);
       String selectedCarbohydrate = pickRandomOption(carbohydrateOptions);
       String selectedVegetable = pickRandomOption(vegetablesOptions);


       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Picked Options");
       alert.setHeaderText(null);
       alert.setContentText("Meat: " + selectedMeat + "\nCarbohydrate: " + selectedCarbohydrate + "\nVegetables: " + selectedVegetable);
       alert.showAndWait();
   }


   private String pickRandomOption(List<String> options) {
       Random random = new Random();
       return options.get(random.nextInt(options.size()));
   }


   private void updateOptionsPane() {
       VBox newOptionsPane = createOptionsPane();
       ((BorderPane) pickNowButton.getParent()).setCenter(newOptionsPane);
   }
}




