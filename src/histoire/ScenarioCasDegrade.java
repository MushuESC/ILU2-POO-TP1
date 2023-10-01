package histoire;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	 public static void main(String[] args) {
	        Etal etal = new Etal(); // Créez une instance d'étal (n'ayant pas été occupé)

	        try {
	            etal.libererEtal(); // Tentez de libérer l'étal non occupé
	            System.out.println("Libération de l'étal réussie.");
	        } catch (Exception e) {
	            System.out.println("Erreur lors de la libération de l'étal : " + e.getMessage());
	        }
	    }
	}
