package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.excel.PersonDataExcelExport;
import com.example.demo.exception.PersonNotFoundException;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @GetMapping("/")
    public String showHomePage() {
        return "homePage";
    }
    @GetMapping("/register")
    public String showRegistrationPage(){
        return "registerPersonPage";
    }
    @PostMapping("/save")
    public String savePerson(@ModelAttribute Person person, Model model){
        Person thePerson  = personService.save(person);
        int id = thePerson.getId();
        String message = "Record with id : '" + id + "' is saved successfully !";
        model.addAttribute("message", message);
        return "registerPersonPage";

    }

    @GetMapping("/getAllPerson")
    public String getAllPerson(@RequestParam(value = "message", required = false) String message, Model model){
        List<Person> allPersons = personService.findAll();
        model.addAttribute("list", allPersons);
        model.addAttribute("message", message);
        return "allPersonsPage";
    }
    @GetMapping("/edit")
    public String showEditPage(Model model, RedirectAttributes attributes, @RequestParam int id){
        String page = null;
        try{
            Person thePerson = personService.getPersonById(id);
            model.addAttribute("person", thePerson);
            page = "editPersonPage";
        }catch (PersonNotFoundException ex){
            ex.printStackTrace();
            attributes.addAttribute("message", ex.getMessage());
            page = "redirect:getAllPerson";
        }
        return page;
    }
    @PostMapping("/update")
    public String updatePerson(@ModelAttribute Person person, RedirectAttributes attributes){
        personService.updatePerson(person);
        int id = person.getId();
        String message = "Person with id "+id+" updated successfully";
        attributes.addAttribute("message", message);
        return "redirect:getAllPerson";
    }
    @GetMapping("/delete")
    public String deletePerson(@RequestParam int id, RedirectAttributes attributes){
        try {
            personService.deletePersonById(id);
            String message = "Person with id "+id+" deleted successfully";
            attributes.addAttribute("message", message);
        }catch (PersonNotFoundException ex){
            ex.printStackTrace();
            attributes.addAttribute("message", ex.getMessage());
        }
        return "redirect:getAllPerson";
    }
    @GetMapping("/excelExport")
    public ModelAndView exportData(){
        ModelAndView mav = new ModelAndView();
        mav.setView(new PersonDataExcelExport());
        //read data from DB
        List<Person> list= personService.findAll();
        //send to excelImpl class
        mav.addObject("list", list);
        return mav;
    }

}
