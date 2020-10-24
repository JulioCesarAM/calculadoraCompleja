package calculadoraComplejas;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class Interfaz extends Application {
	private VBox symbolVbox;
	private ComboBox<Character> symbolCombo;
	Scene initialScene;
	private TextField realN1, imaginarioN1;
	private TextField realN2, imaginarioN2;
	private TextField resultadoR, resultadoI;
	private Label simboloSumaN1, simboloImaginarioN1, simboloSumaN2, simboloImaginarioN2, simboloSumaN3,
			simboloImaginarioN3;
	private VBox zonaOperaciones;
	private Separator separador;
	private HBox n1Hbox, n2Hbox, rHbox, fHbox;
	private Complejo symbole = new Complejo();
	private DoubleProperty realn1 = new SimpleDoubleProperty();
	private DoubleProperty realn2 = new SimpleDoubleProperty();
	private DoubleProperty imagin1 = new SimpleDoubleProperty();
	private DoubleProperty imagin2 = new SimpleDoubleProperty();
	private DoubleProperty totalRealN1 = new SimpleDoubleProperty();
	private DoubleProperty totalImagiN1 = new SimpleDoubleProperty();
	private StringExpression auxReal;
	private StringExpression auxImagin;

	public void start(Stage primaryStage) throws Exception {

		symbolSelector();
		vboxSymbol();
		number1R();
		number2RI();
		numer3Resultados();
		inicializarZonaOperativa();
		HBoxN1S();
		HBoxN2S();
		HBoxN3S();
		vboxZonaOperatoria();
		finalHbox();
		inicializarBindeos();

		symbolCombo.valueProperty().addListener(new ChangeListener<Character>() {

			@Override
			public void changed(ObservableValue<? extends Character> observable, Character o, Character n) {
				if (n == '+') {
					totalRealN1.bind(realn1.add(realn2));
					totalImagiN1.bind(imagin1.add(imagin2));

					auxReal = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalRealN1.asString())
									.otherwise(totalRealN1.asString()));

					resultadoR.textProperty().bind(auxReal);

					auxImagin = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalImagiN1.asString())
									.otherwise(totalImagiN1.asString()));

					resultadoI.textProperty().bind(auxImagin);
				} else if (n == '-') {
					totalRealN1.bind(realn1.subtract(realn2));
					totalImagiN1.bind(imagin1.subtract(imagin2));
					auxReal = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalRealN1.asString())
									.otherwise(totalRealN1.asString()));

					resultadoR.textProperty().bind(auxReal);

					auxImagin = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalImagiN1.asString())
									.otherwise(totalImagiN1.asString()));

					resultadoI.textProperty().bind(auxImagin);

				} else if (n == '*') {
					totalRealN1.bind((realn1.multiply(realn2)).subtract((imagin1.multiply(imagin2))));
					totalImagiN1.bind((realn1.multiply(imagin2)).add((imagin1.multiply(realn2))));
					auxReal = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalRealN1.asString())
									.otherwise(totalRealN1.asString()));

					resultadoR.textProperty().bind(auxReal);

					auxImagin = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalImagiN1.asString())
									.otherwise(totalImagiN1.asString()));

					resultadoI.textProperty().bind(auxImagin);
				} else {
					totalRealN1.bind(((realn1.multiply(realn2).add(imagin1.multiply(imagin2))
							.divide((realn2.multiply(realn2).add(imagin2.multiply(imagin2)))))));
					totalImagiN1.bind((imagin1.multiply(realn1).subtract(realn1.multiply(imagin2)))
							.divide((realn2.multiply(realn2).add(imagin2.multiply(imagin2)))));
					auxReal = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalRealN1.asString())
									.otherwise(totalRealN1.asString()));

					resultadoR.textProperty().bind(auxReal);

					auxImagin = Bindings
							.when(Bindings.isEmpty(realN1.textProperty()).and(Bindings.isEmpty(realN2.textProperty())))
							.then("N/A").otherwise(Bindings.when(realn1.isEqualTo(0)).then(totalImagiN1.asString())
									.otherwise(totalImagiN1.asString()));

					resultadoI.textProperty().bind(auxImagin);

				}

			}

		});

		initialScene = new Scene(fHbox, 500, 500);
		primaryStage.setScene(initialScene);
		primaryStage.setTitle("CalculadoraView");
		primaryStage.show();

	}

	@SuppressWarnings({ "unchecked" })
	private void inicializarBindeos() {
		StringConverter<? extends Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(realN1.textProperty(), realn1, (StringConverter<Number>) converter);
		Bindings.bindBidirectional(realN2.textProperty(), realn2, (StringConverter<Number>) converter);
		Bindings.bindBidirectional(imaginarioN1.textProperty(), imagin1, (StringConverter<Number>) converter);
		Bindings.bindBidirectional(imaginarioN2.textProperty(), imagin2, (StringConverter<Number>) converter);

	}

	private void symbolSelector() {
		symbolCombo = new ComboBox<>();
		symbolCombo.getItems().addAll(symbole.getSuma(), symbole.getResta(), symbole.getDivision(),
				symbole.getMultiplicacion());
	}

	private void vboxSymbol() {
		symbolVbox = new VBox();
		symbolVbox.getChildren().addAll(symbolCombo);
		symbolVbox.setAlignment(Pos.CENTER);

	}

	private void inicializarZonaOperativa() {
		simboloSumaN1 = new Label("+");
		simboloImaginarioN1 = new Label("i");
		simboloSumaN2 = new Label("+");
		simboloImaginarioN2 = new Label("i");
		simboloSumaN3 = new Label("+");
		simboloImaginarioN3 = new Label("i");
		separador = new Separator();
	}

	private void number1R() {
		realN1 = new TextField();
		realN1.setAlignment(Pos.CENTER);
		imaginarioN1 = new TextField();
		imaginarioN1.setAlignment(Pos.CENTER);

	}

	private void number2RI() {
		realN2 = new TextField();
		realN2.setAlignment(Pos.CENTER);
		imaginarioN2 = new TextField();
		imaginarioN2.setAlignment(Pos.CENTER);

	}

	private void numer3Resultados() {
		resultadoR = new TextField();
		resultadoR.setAlignment(Pos.CENTER);
		resultadoR.setDisable(true);
		resultadoI = new TextField();
		resultadoI.setAlignment(Pos.CENTER);
		resultadoI.setDisable(true);

	}

	private void HBoxN1S() {
		n1Hbox = new HBox(5);
		// n1Hbox.setAlignment(pos.CENTER);
		n1Hbox.getChildren().addAll(realN1, simboloSumaN1, imaginarioN1, simboloImaginarioN1);

	}

	private void HBoxN2S() {
		n2Hbox = new HBox(5);
		// n2Hbox.setAlignment(pos.CENTER);
		n2Hbox.getChildren().addAll(realN2, simboloSumaN2, imaginarioN2, simboloImaginarioN2);

	}

	private void HBoxN3S() {
		rHbox = new HBox(5);
		// rHbox.setAlignment(POS.CENTER);
		rHbox.getChildren().addAll(resultadoR, simboloSumaN3, resultadoI, simboloImaginarioN3);

	}

	private void vboxZonaOperatoria() {

		zonaOperaciones = new VBox(5);
		zonaOperaciones.getChildren().addAll(n1Hbox, n2Hbox, separador, rHbox);
		zonaOperaciones.setAlignment(Pos.CENTER);
	}

	private void finalHbox() {
		fHbox = new HBox(10);
		fHbox.getChildren().addAll(symbolVbox, zonaOperaciones);
		fHbox.setAlignment(Pos.CENTER);

	}

	public static void main(String[] args) {
		launch(args);

	}

}
