package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.branch.BranchDto;
import com.example.nomad.nomad.dto.branch.BranchLocationDto;
import com.example.nomad.nomad.service.branch.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@AllArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        List<BranchDto> branches = branchService.getBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        BranchDto branch = branchService.getBranchById(id);
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }
    @GetMapping("/location/{id}")
    public ResponseEntity<BranchLocationDto> getBranchLocationById(@PathVariable Long id) {
        return new ResponseEntity<>(branchService.getLocationOfBranch(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) {
        BranchDto newBranch = branchService.createBranch(branchDto);
        return new ResponseEntity<>(newBranch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) {
       BranchDto updatedBranch = branchService.updateBranch(id, branchDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        boolean deleted = branchService.deleteBranch(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
