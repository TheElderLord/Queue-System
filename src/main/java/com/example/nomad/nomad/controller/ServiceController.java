package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.service.serviceModel.ServService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@AllArgsConstructor
public class ServiceController {
    @Qualifier("serviceServiceImpl")
    private final ServService service;

    @GetMapping
    private ResponseEntity<List<ServiceModelDto>> getServices(){
        List<ServiceModelDto> list = service.getServices();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ServiceModelDto> getServiceById(@PathVariable Long id){
        ServiceModelDto serviceModel = service.getServiceById(id);
        if(serviceModel==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serviceModel);
    }

    @PostMapping
    private ResponseEntity<ServiceModelDto> createService(@RequestBody ServiceModelDto newServiceModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createService(newServiceModel));
    }

    @PutMapping("/{id}")
    private ResponseEntity<ServiceModelDto> updateService(@PathVariable Long id,@RequestBody ServiceModelDto newService){
        return ResponseEntity.ok(service.updateService(id,newService));

    }
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteService(@PathVariable Long id){
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }

}
