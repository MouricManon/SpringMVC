package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import monprojet.dao.*;
import monprojet.entity.City;
import monprojet.entity.Country;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/cities") // This means URL's start with /hello (after Application path)
@Slf4j
public class CityController {

	@Autowired
	private CityRepository dao;
	@Autowired
	private CountryRepository daoC;

	@GetMapping(path = "show") //à l'URL http://localhost:8989/cities/show
	public String montreLesVilles(Model model) {
		model.addAttribute("cities", dao.findAll());
		// On initialise la ville avec des valeurs par défaut
		return "myTemplate";
	}

	@GetMapping(path="add")
public String montreLeFormulaire(Model model){
	Country france = daoC.findById(1).orElseThrow();
	City nouvelle = new City("Nouvelle ville", france);
	nouvelle.setPopulation(50);
	model.addAttribute("cities", dao.findAll());
	model.addAttribute("city", nouvelle);
	model.addAttribute("countries", daoC.findAll());
return "myTemplate2";
	}


	/**
	 * Insère une nouvelle ville dans la base
	 * @param laVille la ville à insérer, initialisée par Spring à partir des valeurs soumises dans le formulaire
	 * Spring fera automatiquement une requête SQL SELECT pour récupérer le pays à partir de son id.	 
	 * Spring fera une requête SQL INSERT pour insérer la ville dans la base
	 * @return
	 */
	@PostMapping(path="save") // Requête HTTP POST à l'URL http://localhost:8989/cities/save
	public String enregistreUneVille(City laVille) {
		dao.save(laVille);
		log.info("La ville {} a été enregistrée", laVille);
		// On redirige vers la page de liste des villes
		return "redirect:/cities/show";
	}

	@GetMapping(path = "delete")
	public String supprimeUneCategoriePuisMontreLaListe(@RequestParam("id") City city) {
		dao.delete(city);
		return "redirect:show"; 
}

@GetMapping(path="edit")
public String montreLeFormulaireEtEdite(Model model,@RequestParam("id") City city ){
	model.addAttribute("cities", dao.findAll());
	model.addAttribute("city", city);
	model.addAttribute("countries", daoC.findAll());
return "myTemplate2";
	}

}