package com.test_thymeleaf.thymeleaf.web.controller;

import com.test_thymeleaf.thymeleaf.form.PersonnageForm;
import com.test_thymeleaf.thymeleaf.model.Personnage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


//affichage d'un personnage par son id
@Controller
public class PersonnageController
{
    // Membres
    private static ArrayList<Personnage> listPersonnages = new ArrayList<Personnage>();

    static {

        Personnage personnage0 = new Personnage ( 0, new String ("Gerard"),new String("Guerrier") );
        Personnage personnage1 = new Personnage ( 1, new String ("Jose"),new String("Magicien") );
        Personnage personnage2 = new Personnage ( 2, new String ("Bernard"),new String("Guerrier") );

        listPersonnages.add(personnage0);
        listPersonnages.add(personnage1);
        listPersonnages.add(personnage2);

    }

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    // Methodes

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    //Personnage/{id}
    @GetMapping(value ="Personnage/{id}")
    public Personnage findById(@PathVariable int id)
    {
        //Personnage personnage = new Personnage ( id, new String ("Gerard"),new String("Guerrier") );
        //return personnage;

        return listPersonnages.get(id);
    }

    //tous les personnages
    //Personnage/list
    @GetMapping(value ="personList")
    public ArrayList<Personnage> afficherListePersonnages(Model model)
    {
        model.addAttribute("persons", listPersonnages);

        return listPersonnages;
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        PersonnageForm personnageForm = new PersonnageForm();
        model.addAttribute("personForm", personnageForm);

        return "addPerson";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePersonnage(Model model, //
                             @ModelAttribute("personForm") PersonnageForm personnageForm) {

        int id = personnageForm.getId();
        String nom = personnageForm.getNom();
        String type = personnageForm.getType();

        if (nom != null && nom.length() > 0 //
                && type != null && type.length() > 0) {

            // TODO : enlever la main sur l'id, et calculer un nouvel id
            Personnage newPersonnage = new Personnage(id, nom, type);
            listPersonnages.add(newPersonnage);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }



    /*
    //Personnages en post qui sert à ajouter un personnage
    @PostMapping(value = "Personnage/list")
    public void creerPersonnage( @RequestBody Personnage personnage)
    {
        listPersonnages.add(personnage);
    }

    @PutMapping(value = "Personnage/modifier/{id}")
    public void modifierPersonnage( @RequestBody Personnage personnage, @PathVariable int id)
    {
        listPersonnages.set(id, personnage);
    }

    @DeleteMapping(value= "Personnage/supprimer/{id}")
    public void supprimerPersonnage(@PathVariable int id)
    {
        listPersonnages.remove(id);
    }*/

}

