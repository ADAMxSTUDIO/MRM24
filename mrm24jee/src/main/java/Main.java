import dao.*;
import model.*;


public class Main {
    public static void main(String[] args) {
        ChambreDAO ch1 = new ChambreDAO();
        ch1.add(new Chambre("CH-001", "Double", 1));
    }
}
