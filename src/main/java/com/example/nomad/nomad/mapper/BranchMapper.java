package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.BranchDto;
import com.example.nomad.nomad.model.Branch;

public class BranchMapper {
    public static BranchDto toDto(Branch branch) {
        if (branch == null) {
            return null;
        }
        BranchDto dto = new BranchDto();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setDescription(branch.getDescription());
        if (branch.getParent() != null) {
            dto.setParentId(branch.getParent().getId());
        }
        return dto;
    }

    public static Branch toEntity(BranchDto dto) {
        if (dto == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(dto.getId());
        branch.setName(dto.getName());
        branch.setDescription(dto.getDescription());
        // ParentBranch needs to be set separately, typically by service layer
        return branch;
    }
}
