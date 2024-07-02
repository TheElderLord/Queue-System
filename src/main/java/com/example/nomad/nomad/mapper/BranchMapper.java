package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.branch.BranchDto;
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
        dto.setLat(branch.getLatitude());
        dto.setLng(branch.getLongitude());
        if (branch.getParent() != null) {
            dto.setParentId(branch.getParent().getId());
            dto.setParentName(branch.getParent().getName());
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
        branch.setLatitude(dto.getLat());
        branch.setLongitude(dto.getLng());
        // ParentBranch needs to be set separately, typically by service layer
        return branch;
    }
}
