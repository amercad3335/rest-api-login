package com.amrsoftware.msvc_role.infrastructure.repository.role;

import com.amrsoftware.msvc_role.domain.model.role.Role;
import com.amrsoftware.msvc_role.domain.model.role.gateways.RoleRepository;
import com.amrsoftware.msvc_role.infrastructure.repository.helper.AdapterOperation;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleDataRepositoryAdapter
    extends AdapterOperation<RoleData, Role, RoleDataRepository>
    implements RoleRepository {
    public RoleDataRepositoryAdapter(RoleDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return repository.findAll().stream().filter(RoleData::isStatus).map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllById(List<Long> ids) {
        return repository.findAllById(ids).stream().filter(RoleData::isStatus).map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        return repository.findById(id).filter(RoleData::isStatus).map(this::toDto);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        role.setDescription(role.getDescription().toUpperCase());
        return toDto(repository.save(toEntity(role)));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDescription(String description) {
        return repository.existsByDescription(description);
    }
}
