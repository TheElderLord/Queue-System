package com.example.nomad.nomad.controller;



import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.service.roleService.RoleServiceService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-services")
@AllArgsConstructor
public class RoleServiceController {
    @Qualifier("roleServiceServiceImpl")
    private final RoleServiceService roleServiceService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);



    @GetMapping
    private ResponseEntity<List<RoleServiceDto>> getRoleServices(){
        List<RoleServiceDto> roleServiceModels = roleServiceService.getRoleServices();
        return ResponseEntity.ok(roleServiceModels);
    }
    @GetMapping("/{id}")
    private ResponseEntity<RoleServiceDto> getRoleServiceById(@PathVariable Long id){
        RoleServiceDto roleServiceModel = roleServiceService.getRoleServiceById(id);
        return ResponseEntity.ok(roleServiceModel);
    }
    @GetMapping("/role/{id}")
    private ResponseEntity<List<RoleServiceDto>> getRoleServicesByRoleId(@PathVariable Long id){
        List<RoleServiceDto> list = roleServiceService.getRoleServicesByRoleId(id);
        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(list);
    }
//    @GetMapping("/branch/{id}")
//    private ResponseEntity<List<RoleServiceDto>> getRoleServicesByBranchId(@PathVariable Long id){
//        List<RoleServiceDto> list = roleServiceService.getRoleServicesByBranch(id);
//        if(list.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        return  ResponseEntity.ok(list);
//    }

    @PostMapping("/role/{roleId}")
    private ResponseEntity<Void> createRoleService(@PathVariable Long roleId, @RequestBody RoleServiceDto[] newRoleServiceModel){
        logger.info("Role service role controller:"+newRoleServiceModel+"\nROLE ID"+roleId);
        roleServiceService.saveRoleService(roleId,newRoleServiceModel);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/{id}")
    private ResponseEntity<Void> updateRoleService(@PathVariable Long id,@RequestBody RoleServiceDto updatedRoleService){
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println(updatedRoleService);
        roleServiceService.updateRoleService(id,updatedRoleService);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteService(@PathVariable Long id){
        roleServiceService.deleteRoleService(id);
        return ResponseEntity.noContent().build();
    }



}
