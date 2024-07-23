package com.amrsoftware.msvc_user.infrastructure.repository.user;

import com.amrsoftware.msvc_user.domain.model.user.User;
import com.amrsoftware.msvc_user.domain.model.user.gateways.UserRepository;
import com.amrsoftware.msvc_user.infrastructure.repository.helper.AdapterOperation;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDataRepositoryAdapter
    extends AdapterOperation<UserData, User, UserDataRepository>
    implements UserRepository {
    public UserDataRepositoryAdapter(UserDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll().stream().filter(UserData::isStatus).map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id).filter(UserData::isStatus).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDto);
    }

    @Override
    @Transactional
    public User save(User user) {
        return toDto(repository.save(toEntity(user)));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void deleteRoleByUserId(Long id) {
        repository.deleteRoleByUserId(id);
    }

    @Override
    @Transactional
    public void deleteRoleByRoleId(Long id) {
        repository.deleteRoleByRoleId(id);
    }
}
