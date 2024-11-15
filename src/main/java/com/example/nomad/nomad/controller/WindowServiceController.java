package com.example.nomad.nomad.controller;




import com.example.nomad.nomad.dto.WindowServiceModelDto;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import com.example.nomad.nomad.service.windowService.WinServiceService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/window-services")
@AllArgsConstructor
public class WindowServiceController {
    @Qualifier("winServiceServiceImpl")
    private final WinServiceService winServiceService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);



    @GetMapping
    private ResponseEntity<List<WindowServiceModelDto>> getwindowServices(){
        List<WindowServiceModelDto> windowServiceModels = winServiceService.getWindowServices();
        return ResponseEntity.ok(windowServiceModels);
    }
    @GetMapping("/{id}")
    private ResponseEntity<WindowServiceModelDto> getwindowServiceById(@PathVariable Long id){
        WindowServiceModelDto windowServiceModel = winServiceService.getWindowServiceById(id);
        return ResponseEntity.ok(windowServiceModel);
    }
    @GetMapping("/window/{id}")
    private ResponseEntity<List<WindowServiceModelDto>> getwindowServicesBywindowId(@PathVariable Long id){
        List<WindowServiceModelDto> list = winServiceService.getWindowServicesByWindowId(id);
        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(list);
    }
//    @GetMapping("/branch/{id}")
//    private ResponseEntity<List<windowServiceDto>> getwindowServicesByBranchId(@PathVariable Long id){
//        List<windowServiceDto> list = windowServiceService.getwindowServicesByBranch(id);
//        if(list.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        return  ResponseEntity.ok(list);
//    }

    @PostMapping("/window/{windowId}")
    private ResponseEntity<Void> createwindowService(@PathVariable Long windowId, @RequestBody WindowServiceModelDto[] newwindowServiceModel){
        logger.info("window service window controller:"+ Arrays.toString(newwindowServiceModel) +"\nwindow ID"+windowId);
        winServiceService.saveWindowService(windowId,newwindowServiceModel);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/{id}")
    private ResponseEntity<Void> updatewindowService(@PathVariable Long id,@RequestBody WindowServiceModelDto[] updatedwindowService){
//        if (id == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        System.out.println(updatedwindowService);
        winServiceService.updateWindowService(id,updatedwindowService);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteService(@PathVariable Long id){
        winServiceService.deleteWindowService(id);
        return ResponseEntity.noContent().build();
    }



}
