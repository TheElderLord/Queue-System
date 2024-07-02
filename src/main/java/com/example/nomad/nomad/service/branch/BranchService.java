package com.example.nomad.nomad.service.branch;

import com.example.nomad.nomad.dto.branch.BranchDto;
import com.example.nomad.nomad.dto.branch.BranchLocationDto;
import com.example.nomad.nomad.model.Branch;

import java.util.List;

public interface BranchService {

    List<BranchDto> getBranches();
    BranchLocationDto getLocationOfBranch(Long id);

    BranchDto getBranchById(Long id);

    BranchDto createBranch(BranchDto branchDto);

    BranchDto updateBranch(Long id, BranchDto newBranchDto);

    boolean deleteBranch(Long id);

    Branch getEntityById(Long id);
}
