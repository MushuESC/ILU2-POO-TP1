package villagegaulois;
import java.util.ArrayList;
import java.util.List;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
	    this.nom = nom;
	    villageois = new Gaulois[nbVillageoisMaximum];
	    marche = new Marche(nbEtalsMarche); // Créez le marché avec le nombre d'étals
	}
	
	   private static class Marche {
	        private Etal[] etals;
	        private int nbEtalVide; // Nombre d'étals non utilisés

	        private Marche(int tailleMarche) {
	            etals = new Etal[tailleMarche];
	            for (int i = 0; i < tailleMarche; i++) {
	                etals[i] = new Etal();
	            }
	            nbEtalVide = tailleMarche;
	        }

	        private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
	            if (indiceEtal >= 0 && indiceEtal < etals.length && etals[indiceEtal] != null && nbEtalVide > 0) {
	                etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
	                nbEtalVide--;
	            }
	        }

	        private int trouverEtalLibre() {
	            for (int i = 0; i < etals.length; i++) {
	                if (etals[i] != null && !etals[i].isEtalOccupe()) {
	                    return i;
	                }
	            }
	            return -1; // Aucun étal disponible
	        }

	        private Etal[] trouverEtals(String produit) {
	            List<Etal> etalsAvecProduit = new ArrayList<>();
	            for (Etal etal : etals) {
	                if (etal != null && etal.contientProduit(produit)) {
	                    etalsAvecProduit.add(etal);
	                }
	            }
	            return etalsAvecProduit.toArray(new Etal[0]);
	        }

	        private Etal trouverVendeur(Gaulois gaulois) {
	            for (Etal etal : etals) {
	                if (etal != null && etal.getVendeur() == gaulois) {
	                    return etal;
	                }
	            }
	            return null;
	        }

	        private String afficherMarche() {
	            StringBuilder chaine = new StringBuilder();
	            for (Etal etal : etals) {
	                if (etal != null && etal.isEtalOccupe()) {
	                    chaine.append(etal.afficherEtal());
	                }
	            }
	            if (nbEtalVide > 0) {
	                chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
	            }
	            return chaine.toString();
	        }
	        
	        
	    }
	
	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
	    if (chef == null) {
	        throw new VillageSansChefException("Le village ne peut exister sans chef.");
	    }
	    StringBuilder chaine = new StringBuilder();
	    if (nbVillageois < 1) {
	        chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
	    } else {
	        chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
	        for (int i = 0; i < nbVillageois; i++) {
	            chaine.append("- " + villageois[i].getNom() + "\n");
	        }
	    }
	    return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    int indiceEtalLibre = marche.trouverEtalLibre();
	    if (indiceEtalLibre != -1) {
	        marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
	        return vendeur.getNom() + " s'installe à l'étal " + indiceEtalLibre + " et vend " + nbProduit + " " + produit + ".\n";
	    } else {
	        return vendeur.getNom() + " ne peut pas s'installer, tous les étals sont occupés.\n";
	    }
	}

	public String rechercherVendeursProduit(String produit) {
	    Etal[] etalsAvecProduit = marche.trouverEtals(produit);
	    StringBuilder chaine = new StringBuilder();
	    chaine.append("Les vendeurs de " + produit + " sont :\n");
	    for (Etal etal : etalsAvecProduit) {
	        chaine.append("- " + etal.getVendeur().getNom() + "\n");
	    }
	    return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
	    return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
	    Etal etal = marche.trouverVendeur(vendeur);
	    if (etal != null) {
	        String message = etal.libererEtal(); // Appel de la méthode libererEtal de la classe Etal
	        return vendeur.getNom() + " quitte son étal. " + message;
	    } else {
	        return vendeur.getNom() + " n'a pas d'étal où partir.\n";
	    }
	}

	public String afficherMarche() {
	    return marche.afficherMarche();
	}
	
	public class VillageSansChefException extends Exception {
	    private static final long serialVersionUID = 1L; 
	    public VillageSansChefException(String message) {
	        super(message);
	    }
	}
}



