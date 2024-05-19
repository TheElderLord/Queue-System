package com.example.nomad.nomad.service.branch;

import com.example.nomad.nomad.dto.BranchDto;
import com.example.nomad.nomad.model.Branch;

import java.util.List;

public interface BranchService {

    List<BranchDto> getBranches();

    BranchDto getBranchById(Long id);

    BranchDto createBranch(BranchDto branchDto);

    boolean updateBranch(Long id, BranchDto newBranchDto);

    boolean deleteBranch(Long id);

    Branch getEntityById(Long id);
}
