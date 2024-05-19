package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.BranchDto;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.branch.impl.BranchServiceImpl;
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

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) {
        BranchDto newBranch = branchService.createBranch(branchDto);
        return new ResponseEntity<>(newBranch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) {
        boolean updated = branchService.updateBranch(id, branchDto);
        if (updated) {
            BranchDto updatedBranch = branchService.getBranchById(id);
            return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
