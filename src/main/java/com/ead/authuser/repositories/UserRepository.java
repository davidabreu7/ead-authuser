package com.ead.authuser.repositories;

import com.ead.authuser.models.QUserModel;
import com.ead.authuser.models.UserModel;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<UserModel, String>, QuerydslPredicateExecutor<UserModel>,
        QuerydslBinderCustomizer<QUserModel> {

    Optional<UserModel> findById(String id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    @Override
    default void customize(QuerydslBindings bindings, QUserModel root) {
        bindings.bind(root.username, root.email, root.fullname).all((path, value) -> {
            BooleanBuilder builder = new BooleanBuilder();
            value.forEach(v -> builder.or(path.containsIgnoreCase(v)));
            return Optional.of(builder);
        });
        bindings.bind(root.courses).first((path, value) -> {
            BooleanBuilder builder = new BooleanBuilder();
            value.forEach(course -> builder.or(root.courses.contains(course)));
            return builder;
        });

    }

}

