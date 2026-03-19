package com.example.demo.service;

import com.example.demo.entity.Director;
import com.example.demo.exception.DirectorNotFoundException;
import com.example.demo.repository.DirectorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
       private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

       //CRUD METHODS
    public Director getDirector(Integer id) {
        return directorRepository.findById(id).orElseThrow( () -> new DirectorNotFoundException("Director not found with id :" + id));
    }

    public List<Director>  getAllDirectors() {
        return directorRepository.findAll();
    }

      public Director newDirector(Director director) {
        return directorRepository.save(director);
      }

      public void deleteDirector(Integer id) {
        if (!directorRepository.existsById(id)){
            throw new DirectorNotFoundException("Director not found with id :" + id
            );
        }
          directorRepository.deleteById(id);
      }

        public Director updateDirector(Integer id, Director updatedDirector){
            return  directorRepository.findById(id).map( exsisting -> {
                exsisting.setName(updatedDirector.getName());
                exsisting.setAge(updatedDirector.getAge());
                exsisting.setBirthday(updatedDirector.getBirthday());
                exsisting.setSur_name(updatedDirector.getSur_name());
                return  directorRepository.save(exsisting);
            }).orElseThrow(() -> new DirectorNotFoundException("Director not found with id :" + id));
        }
    //REPOSITORY METHODS
    public List<Director> findAllDirectorsByName(String name){
        return directorRepository.findByName(name);
    }

    public List<Director> findAllDirectorsBySurname(String sur_name){
        return directorRepository.findBySurname(sur_name);
    }

    public List<Director> findAllDirectorsByBirthday(String birthday){
        return directorRepository.findByBirthday(birthday);
    }

    //SEARCH
    public List<Director> searchDirector(String name, String sur_name, Sort sort){
        if(name !=null && !name.isBlank()){
            directorRepository.findByNameContainingIgnoreCase(name);
        }

        if (sur_name !=null && !sur_name.isBlank()){
            directorRepository.findBySurnameContainingIgnoreCase(sur_name);
        }
        return directorRepository.findAll(sort);
    }
}
