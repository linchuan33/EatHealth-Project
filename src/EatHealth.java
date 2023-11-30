import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EatHealth extends Application {
    private static final List<String> MEAT_OPTIONS = Arrays.asList("Chicken", "Beef", "Fish", "Shellfish", "Duck", "Lamb");
    private static final List<String> CARBOHYDRATE_OPTIONS = Arrays.asList("Rice", "Noodle");
    private static final List<String> VEGETABLES_OPTIONS = Arrays.asList("Potatoes", "Tomatoes", "Carrots", "Lettuce", "Cucumbers", "Mushrooms");

    private Button pickNowButton;
    private HBox meatBox, carbohydrateBox, vegetablesBox;

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

    private HBox createOptionsPane() {
        meatBox = createFoodBox("Meat", MEAT_OPTIONS);
        carbohydrateBox = createFoodBox("Carbohydrate", CARBOHYDRATE_OPTIONS);
        vegetablesBox = createFoodBox("Vegetables", VEGETABLES_OPTIONS);

        HBox optionsPane = new HBox(meatBox, carbohydrateBox, vegetablesBox);
        optionsPane.setAlignment(Pos.CENTER);
        optionsPane.setSpacing(20);
        return optionsPane;
    }

    private HBox createFoodBox(String category, List<String> options) {
        HBox foodBox = new HBox();
        foodBox.setAlignment(Pos.CENTER);
        foodBox.setSpacing(10);

        Button addButton = createAddButton(category);
        foodBox.getChildren().add(addButton);

        Label categoryLabel = new Label(category + ":");
        foodBox.getChildren().add(categoryLabel);

        for (String option : options) {
            HBox optionBox = createOptionBox(option, category);
            foodBox.getChildren().add(optionBox);
        }

        return foodBox;
    }

    private HBox createOptionBox(String option, String category) {
        HBox optionBox = new HBox();
        optionBox.setAlignment(Pos.CENTER_LEFT);

        Button deleteButton = createDeleteButton(option, category);

        Label optionLabel = new Label(option);
        optionLabel.setStyle("-fx-border-color: black; -fx-padding: 5px;");

        optionBox.getChildren().addAll(deleteButton, optionLabel);
        return optionBox;
    }

    private Button createDeleteButton(String option, String category) {
        Button deleteButton = new Button("-");
        deleteButton.setOnAction(e -> {
            //此处可以从列表中删除所选选项（外形待定）
            if (MEAT_OPTIONS.remove(option) || CARBOHYDRATE_OPTIONS.remove(option) || VEGETABLES_OPTIONS.remove(option)) {
                // Update the UI
                updateOptionsPane();
            }
        });

        return deleteButton;
    }

    private Button createAddButton(String category) {
        Button addButton = new Button("+");
        addButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Option");
            dialog.setHeaderText("Enter the new " + category.toLowerCase() + " option:");
            dialog.setContentText("Option:");

            dialog.showAndWait().ifPresent(newOption -> {
                // Add the new option to the appropriate list
                if (category.equals("Meat")) {
                    MEAT_OPTIONS.add(newOption);
                } else if (category.equals("Carbohydrate")) {
                    CARBOHYDRATE_OPTIONS.add(newOption);
                } else if (category.equals("Vegetables")) {
                    VEGETABLES_OPTIONS.add(newOption);
                }

                //UI
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
        // 从每个类别中选择一个选项（三大选项分类）
        String selectedMeat = pickRandomOption(MEAT_OPTIONS);
        String selectedCarbohydrate = pickRandomOption(CARBOHYDRATE_OPTIONS);
        String selectedVegetable = pickRandomOption(VEGETABLES_OPTIONS);

        // 显示选项
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
        // Re-create选项窗（但是好像没啥用。。。
        HBox newOptionsPane = createOptionsPane();
        ((BorderPane) pickNowButton.getParent()).setCenter(newOptionsPane);
    }
}
