package com.pfe.gestionnote.web.rest;

import java.util.List;

import com.pfe.gestionnote.domain.EtudiantByGroup;
import com.pfe.gestionnote.domain.GroupEnsiegner;
import com.pfe.gestionnote.domain.MatiereByEnseignement;
import com.pfe.gestionnote.service.NoteCCService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class NoteccRessource {
    @Autowired
    private NoteCCService noteccService;

    @GetMapping("/getmatierebyenseignement/{idEnseignent}")
    public List<MatiereByEnseignement> getmatierebyenseignement(@PathVariable Integer idEnseignent){
        return noteccService.getmatierebyenseignement(idEnseignent);
    }
    @GetMapping("/getEtudiantByGroup/{idgroup}")
    public List<EtudiantByGroup> getEtudiantByGroup(@PathVariable Integer idgroup){
        return noteccService.getEtudiantByGroup(idgroup);
    }
    @GetMapping("/getGroupEnseigner/{idgroup}")
    public List<GroupEnsiegner> getGroupEnseigner(@PathVariable Integer idgroup){
        return noteccService.getGroupEnseigner(idgroup);
    }
    
}