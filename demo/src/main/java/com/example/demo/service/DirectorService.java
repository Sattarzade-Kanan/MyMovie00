package com.example.demo.service;

import com.example.demo.dto.DirectorDTO;
import com.example.demo.entity.Director;
import com.example.demo.exception.DirectorNotFoundException;
import com.example.demo.mapper.DirectorMapper;
import com.example.demo.repository.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
       private final DirectorRepository directorRepository;
       private final DirectorMapper directorMapper;

    public DirectorService(DirectorRepository directorRepository, DirectorMapper directorMapper) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
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
                exsisting.setSurname(updatedDirector.getSurname());
                return  directorRepository.save(exsisting);
            }).orElseThrow(() -> new DirectorNotFoundException("Director not found with id :" + id));
        }
    //REPOSITORY METHODS




    //SEARCH
    public List<Director> searchDirector(String name, String surname, Sort sort){
        if(name !=null && !name.isBlank()){
            directorRepository.findByNameContainingIgnoreCase(name);
        }

        if (surname !=null && !surname.isBlank()){
            directorRepository.findBySurnameContainingIgnoreCase(surname);
        }
        return directorRepository.findAll(sort);
    }

        public Page<DirectorDTO> searchPaginated(String name,String surname,int page,int size,Sort sort){
            Pageable pageable = PageRequest.of(page,size,sort);
            String safeName = (name == null) ? "" : name;
            String safeSurname = (surname == null) ? "" : surname;

            Page<Director> directorPage =
                    directorRepository.paginationNeed(
                            safeName, safeSurname , pageable);
            return directorPage.map(directorMapper::toDTO);
        }
}
