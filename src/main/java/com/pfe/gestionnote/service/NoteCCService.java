package com.pfe.gestionnote.service;

import java.util.List;

import com.pfe.gestionnote.repository.EtudiantByGroupRepository;
import com.pfe.gestionnote.repository.GroupEnsiegnerRepository;
import com.pfe.gestionnote.repository.MatiereByEnseignementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import  com.pfe.gestionnote.domain.MatiereByEnseignement;
import  com.pfe.gestionnote.domain.GroupEnsiegner;
import  com.pfe.gestionnote.domain.EtudiantByGroup;
import org.springframework.stereotype.Service;

@Service
public class NoteCCService {
    @Autowired
    private EtudiantByGroupRepository etudiantByGroupRepository;
    @Autowired
    private GroupEnsiegnerRepository GroupEnsiegnerRepository;
    @Autowired
    MatiereByEnseignementRepository matiereByEnseignementRepository;
    public List<MatiereByEnseignement> getmatierebyenseignement(Integer idEnseignent){
        return matiereByEnseignementRepository.getmatierebyenseignement(idEnseignent);
    }
    public List<EtudiantByGroup> getEtudiantByGroup(Integer idgroup){
        return etudiantByGroupRepository.getEtudiantByGroup(idgroup);
    }
    public List<GroupEnsiegner> getGroupEnseigner(Integer idgroup){
        return GroupEnsiegnerRepository.getGroupEnseigner(idgroup);
    }
}