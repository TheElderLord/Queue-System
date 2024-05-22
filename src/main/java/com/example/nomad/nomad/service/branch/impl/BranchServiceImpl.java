package com.example.nomad.nomad.service.branch.impl;

import com.example.nomad.nomad.dto.BranchDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.BranchMapper;
import com.example.nomad.nomad.model.Branch;
import com.example.nomad.nomad.repository.BranchRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public List<BranchDto> getBranches() {
        return branchRepository.findAll().stream().map(BranchMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(Long id) {
        return BranchMapper.toDto(getEntityById(id));
    }

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = BranchMapper.toEntity(branchDto);

        if (branchDto.getParentId() != null) {
            Branch existingBranch = getEntityById(branchDto.getParentId());
            branch.setParent(existingBranch);
        }
        return BranchMapper.toDto(branchRepository.save(branch));
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto newBranchDto) {
        Branch existingBranch = getEntityById(id);
        existingBranch.setName(newBranchDto.getName());
        existingBranch.setDescription(newBranchDto.getDescription());

        if (newBranchDto.getParentId() != null) {
            Branch parentBranch = getEntityById(newBranchDto.getParentId());
            existingBranch.setParent(parentBranch);

        }

        return BranchMapper.toDto(branchRepository.save(existingBranch));

    }

    @Override
    public boolean deleteBranch(Long id) {
        Branch branch = getEntityById(id);
        if (branch != null) {
            branchRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Branch getEntityById(Long id) {
        return branchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + id));
    }
}
