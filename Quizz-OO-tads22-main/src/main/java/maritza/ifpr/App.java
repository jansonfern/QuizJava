package maritza.ifpr;


import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private ControladorQuiz controladorQuiz;

    private VBox root;
    private Scene cena;
    private Text enunciado;
    private Button alternativa1;
    private Button alternativa2;
    private Button alternativa3;
    private Button alternativa4;
    private Button alternativa5;
    private Text resultado;
    private Button proxima;

    @Override
    public void init() throws Exception {
        super.init();

        ArrayList<Questao> lista = new ArrayList<>();

        lista.add(new Questao("Qual a cor favorita da prof?", "rosa",
                new String[] { "preto", "laranja", "roxo", "vermelho" }));
        lista.add(new Questao("Qual a cor favorita da prof 2?", "verde",
                new String[] { "preto", "laranja", "roxo", "vermelho" }));
        lista.add(new Questao("Qual a cor favorita da prof? 3", "azul",
                new String[] { "preto", "laranja", "roxo", "vermelho" }));
        lista.add(new Questao("Qual a cor favorita da prof? 4", "cinza",
                new String[] { "preto", "laranja", "roxo", "vermelho" }));
        lista.add(new Questao("Qual a cor favorita da prof? 5", "marrom",
                new String[] { "preto", "laranja", "roxo", "vermelho" }));

        controladorQuiz = new ControladorQuiz(lista);

    }

    @Override
    public void start(Stage stage) throws Exception {

        inicializaComponentes();
        atualizaComponentes();

        cena = new Scene(root, 600, 600);
        cena.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(cena);
        stage.show();

    }

    public void inicializaComponentes() {
        enunciado = new Text("Qual a cor favorita da prof?");
        alternativa1 = new Button("Questão 1");
        alternativa2 = new Button("Questão 2");
        alternativa3 = new Button("Questão 3");
        alternativa4 = new Button("Questão 4");
        alternativa5 = new Button("Questão 5");
        

        resultado = new Text("Resultado");
        proxima = new Button("Próxima");

        root = new VBox();
        root.getChildren().add(enunciado);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10.0);

        root.getChildren().add(alternativa1);
        root.getChildren().add(alternativa2);
        root.getChildren().add(alternativa3);
        root.getChildren().add(alternativa4);
        root.getChildren().add(alternativa5);

        root.getChildren().add(resultado);
        root.getChildren().add(proxima);
        resultado.setVisible(false);
        proxima.setVisible(false);

        alternativa1.setOnAction(respondeQuestao());
        alternativa2.setOnAction(respondeQuestao());
        alternativa3.setOnAction(respondeQuestao());
        alternativa4.setOnAction(respondeQuestao());
        alternativa5.setOnAction(respondeQuestao());
        proxima.setOnAction(proximaQuestao());

    }

    public void atualizaComponentes() {

        Questao objQuestao = controladorQuiz.getQuestao();
        ArrayList<String> questoes = objQuestao.getTodasAlternativas();

        alternativa1.setText(questoes.get(0));
        alternativa2.setText(questoes.get(1));
        alternativa3.setText(questoes.get(2));
        alternativa4.setText(questoes.get(3));
        alternativa5.setText(questoes.get(4));

    }

    
    private EventHandler respondeQuestao() {
        return new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Button clicado = (Button) event.getSource();
                String alternativa = clicado.getText();

                boolean result = controladorQuiz.respondeQuestao(alternativa);

                if (result) {
                    resultado.setText("Acertou!");
                }else{
                    resultado.setText("Vc errou!");
                }
                resultado.setVisible(true);
                proxima.setVisible(true);
            }
        };
    }

    private EventHandler proximaQuestao() {
        return new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (controladorQuiz.temProximaQuestao()) {
                    controladorQuiz.proximaQuestao();
                    atualizaComponentes();
                }
                resultado.setVisible(false);
                proxima.setVisible(false);
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}