import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {

        ScientificCalculator scientificCalculator = new ScientificCalculator();

        scientificCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scientificCalculator.setSize(500, 450);
        scientificCalculator.setVisible(true);


    }
}


